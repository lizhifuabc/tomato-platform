package com.tomato.account.service.trad;

import com.tomato.account.constant.AccountRespCode;
import com.tomato.account.domain.bo.AccountBalanceBO;
import com.tomato.account.domain.bo.AccountHisBO;
import com.tomato.account.domain.dto.AccountTradDto;
import com.tomato.account.domain.entity.AccountHisEntity;
import com.tomato.account.domain.entity.AccountInfoEntity;
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

import java.math.BigDecimal;

/**
 * 账户交易
 *
 * @author lizhifu
 * @since 2023/1/7
 */
@Service("accountDeductService")
@Slf4j
public class AccountDeductService implements AccountTradService{
    private final AccountInfoManager accountInfoManager;
    private final AccountHisManager accountHisManager;
    public AccountDeductService(AccountInfoManager accountInfoManager, AccountHisManager accountHisManager) {
        this.accountInfoManager = accountInfoManager;
        this.accountHisManager = accountHisManager;
    }

    /**
     * 账户扣款
     * @param accountTradDto 账户扣款
     */
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public void exe(AccountTradDto accountTradDto){
        log.info("账户扣款 start,{}", accountTradDto);
        AccountInfoEntity account = accountInfoManager.selectByAccountNo(accountTradDto.getAccountNo()).orElseThrow(()-> new BusinessException(AccountRespCode.ACCOUNT_NOT_EXIST));
        // 1.检查账户是否存在
        AccountCheckService.checkAccountExist(account);
        // 2.余额校验
        AccountCheckService.checkBalance(account,accountTradDto.getAmount());
        // 3.校验交易金额
        validateAmount(accountTradDto.getAmount());
        // 2.是否可以出款
        AccountCheckService.checkDeduct(account.getAccountStatus());

        // 4.创建账户历史
        AccountHisBO accountHisBO = BeanUtil.copy(accountTradDto,AccountHisBO.class);
        accountHisBO.setAccountHisType(AccountHisTypeEnum.TRAD.getValue());
        AccountHisEntity accountHisEntity = accountHisManager.insert(account,accountHisBO);

        // 3.执行账户出款
        AccountBalanceBO accountBalanceBO = new AccountBalanceBO();
        accountBalanceBO.setAccountNo(account.getAccountNo());
        accountBalanceBO.setVersion(account.getVersion());
        accountBalanceBO.setAmount(accountHisEntity.getAmount().subtract(accountHisEntity.getAmountFree()));
        accountInfoManager.deduct(accountBalanceBO,account);
        log.info("账户扣款 end,{},accountHisEntity:{}",accountTradDto, accountHisEntity);
    }
    /**
     * 账户扣款-只创建账户历史
     * @param accountTradDto 账户扣款
     */
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public void exeAsync(AccountTradDto accountTradDto){
        log.info("账户扣款 async start,{}", accountTradDto);
        AccountInfoEntity account = accountInfoManager.selectByAccountNo(accountTradDto.getAccountNo()).orElseThrow(()-> new BusinessException(AccountRespCode.ACCOUNT_NOT_EXIST));
        // 1.检查账户是否存在
        AccountCheckService.checkAccountExist(account);
        // 2.余额校验
        AccountCheckService.checkBalance(account,accountTradDto.getAmount());
        // 3.校验交易金额
        validateAmount(accountTradDto.getAmount());
        // 2.是否可以出款
        AccountCheckService.checkDeduct(account.getAccountStatus());
        // 4.创建账户历史
        AccountHisBO accountHisBO = BeanUtil.copy(accountTradDto,AccountHisBO.class);
        accountHisBO.setAccountHisType(AccountHisTypeEnum.TRAD.getValue());
        AccountHisEntity accountHisEntity = accountHisManager.insertAsync(account,accountHisBO);
        log.info("账户扣款 async end,{},accountHisEntity:{}",accountTradDto, accountHisEntity);
    }
    /**
     * 验证交易金额
     * @param amount 交易金额
     */
    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("交易金额错误");
        }
    }
}
