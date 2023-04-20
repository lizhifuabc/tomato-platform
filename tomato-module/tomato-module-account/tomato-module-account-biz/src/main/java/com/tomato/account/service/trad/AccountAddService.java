package com.tomato.account.service.trad;

import com.tomato.account.constant.AccountRespCode;
import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.domain.bo.AccountBalanceBO;
import com.tomato.account.domain.bo.AccountHisBO;
import com.tomato.account.domain.dto.AccountTradDto;
import com.tomato.account.domain.entity.AccountHisEntity;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.req.AccountTradReq;
import com.tomato.account.enums.AccountHisTypeEnum;
import com.tomato.account.manager.AccountHisManager;
import com.tomato.account.manager.AccountInfoManager;
import com.tomato.account.service.AccountCheckService;
import com.tomato.domain.core.exception.BusinessException;
import com.tomato.web.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

/**
 * 账户入账
 *
 * @author lizhifu
 * @since 2023/4/17
 */
@Service("accountAddService")
@Slf4j
public class AccountAddService implements AccountTradService {
    private final AccountInfoManager accountInfoManager;
    private final AccountHisManager accountHisManager;

    public AccountAddService(AccountInfoManager accountInfoManager, AccountHisManager accountHisManager) {
        this.accountInfoManager = accountInfoManager;
        this.accountHisManager = accountHisManager;
    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public void exe(AccountTradDto accountTradDto){
        log.info("账户入账 start,{}", accountTradDto);
        AccountInfoEntity account = accountInfoManager.selectByAccountNo(accountTradDto.getAccountNo()).orElseThrow(()-> new BusinessException(AccountRespCode.ACCOUNT_NOT_EXIST));
        // 基础校验
        baseCheck(accountTradDto,account);

        // 创建账户历史
        AccountHisBO accountHisBO = BeanUtil.copy(accountTradDto,AccountHisBO.class);
        accountHisBO.setAccountHisType(AccountHisTypeEnum.TRAD.getValue());
        AccountHisEntity accountHisEntity = accountHisManager.insert(account,accountHisBO);

        // 执行账户入账
        AccountBalanceBO accountBalanceBO = new AccountBalanceBO();
        accountBalanceBO.setAccountNo(account.getAccountNo());
        accountBalanceBO.setVersion(account.getVersion());
        accountBalanceBO.setAmount(accountHisEntity.getAmount().subtract(accountHisEntity.getAmountFree()));
        accountInfoManager.add(accountBalanceBO,account);
        log.info("账户入账 end,{},accountHisEntity:{}",accountTradDto, accountHisEntity);
    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public void exeAsync(AccountTradDto accountTradDto){
        log.info("账户入账 async start,{}", accountTradDto);
        AccountInfoEntity account = accountInfoManager.selectByAccountNo(accountTradDto.getAccountNo()).orElseThrow(()-> new BusinessException(AccountRespCode.ACCOUNT_NOT_EXIST));
        // 1.基础校验
        baseCheck(accountTradDto,account);
        // 2.创建账户历史
        AccountHisBO accountHisBO = BeanUtil.copy(accountTradDto,AccountHisBO.class);
        accountHisBO.setAccountHisType(AccountHisTypeEnum.TRAD.getValue());
        AccountHisEntity accountHisEntity = accountHisManager.insertAsync(account,accountHisBO);
        log.info("账户入账 async end,{},accountHisEntity:{}",accountTradDto, accountHisEntity);
    }

    /**
     * 基础校验
     * @param accountTradDto 账户加钱
     * @param account 账户信息
     */
    private void baseCheck(AccountTradDto accountTradDto, AccountInfoEntity account){
        // 1.检查账户是否存在
        AccountCheckService.checkAccountExist(account);
        // 2.是否可以收款
        AccountCheckService.checkAdd(account.getAccountStatus());
        // 3.校验交易金额
        if (accountTradDto.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("交易金额错误");
        }
    }
}
