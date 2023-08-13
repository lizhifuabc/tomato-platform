package com.tomato.account.service;

import com.tomato.account.constant.AccountRespCode;
import com.tomato.account.dao.AccountHisDao;
import com.tomato.account.domain.bo.AccountBalanceBO;
import com.tomato.account.domain.bo.AccountHisUpdateBatchBO;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.manager.AccountInfoManager;
import com.tomato.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * 异步入账
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Service
@Slf4j
public class AccountAsyncService {
    private final AccountHisDao accountHisDao;
    private final AccountInfoManager accountInfoManager;
    public AccountAsyncService(AccountHisDao accountHisDao, AccountInfoManager accountInfoManager) {
        this.accountHisDao = accountHisDao;
        this.accountInfoManager = accountInfoManager;
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void async(AccountHisUpdateBatchBO accountHisUpdateBatchDO){
        AccountInfoEntity accountInfoEntity = accountInfoManager.selectByAccountNo(accountHisUpdateBatchDO.getAccountNo()).orElseThrow(()-> new BusinessException(AccountRespCode.ACCOUNT_NOT_EXIST));

        AccountBalanceBO accountBalanceBO = new AccountBalanceBO();
        accountBalanceBO.setAccountNo(accountInfoEntity.getAccountNo());
        accountBalanceBO.setVersion(accountInfoEntity.getVersion());
        accountBalanceBO.setAmount(accountHisUpdateBatchDO.getSum());
        if(accountBalanceBO.getAmount().compareTo(BigDecimal.ZERO) < 0 ){
            // 扣款
            accountInfoManager.deduct(accountBalanceBO,accountInfoEntity);
        }else {
            // 加款
            accountInfoManager.add(accountBalanceBO,accountInfoEntity);
        }
        // 金额为0，此时虽然不需要更新账户余额，但是需要更新账户历史表状态
        accountHisUpdateBatchDO.setBeforeBalance(accountInfoEntity.getBalance());
        accountHisUpdateBatchDO.setAfterBalance(accountInfoEntity.getBalance().add(accountHisUpdateBatchDO.getSum()));
        accountHisUpdateBatchDO.setAccountHisIdList(Arrays.asList(accountHisUpdateBatchDO.getAccountHisIds().split(",")));
        accountHisUpdateBatchDO.setAccountHisSerial(accountInfoEntity.getAccountHisSerial());

        int count = accountHisDao.updateAccountStatusBatch(accountHisUpdateBatchDO);
        if(accountHisUpdateBatchDO.getAccountHisIdList().size() != count){
            log.error("异步入账更新条数异常,期待:{},更新:{}",accountHisUpdateBatchDO.getAccountHisIdList().size(),count);
            throw new BusinessException("更新条数异常");
        }
    }
}
