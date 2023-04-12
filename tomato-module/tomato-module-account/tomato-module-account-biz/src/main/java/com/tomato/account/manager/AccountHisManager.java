package com.tomato.account.manager;

import com.tomato.account.dao.AccountHisDao;
import com.tomato.account.domain.bo.AccountHisBO;
import com.tomato.account.domain.entity.AccountHisEntity;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.domain.core.enums.CommonStatusEnum;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 账户历史
 *
 * @author lizhifu
 * @since 2023/1/1
 */
@Service
public class AccountHisManager {
    private final AccountHisDao accountHisDao;

    public AccountHisManager(AccountHisDao accountHisDao) {
        this.accountHisDao = accountHisDao;
    }

    public AccountHisEntity insert(AccountInfoEntity account, AccountHisBO accountHisBO){
        AccountHisEntity accountHisEntity = baseAccountHis(account,accountHisBO);
        accountHisEntity.setBeforeBalance(account.getBalance());
        accountHisEntity.setAfterBalance(account.getBalance().add(accountHisBO.getAmount()));
        accountHisEntity.setAccountHisSerial(account.getAccountHisSerial());
        accountHisEntity.setAccountStatus(CommonStatusEnum.SUCCESS.getValue());
        accountHisEntity.setCompleteTime(LocalDateTime.now());
        accountHisDao.insert(accountHisEntity);
        return accountHisEntity;
    }
    public AccountHisEntity insertAsync(AccountInfoEntity account, AccountHisBO accountHisBO){
        AccountHisEntity accountHisEntity = baseAccountHis(account,accountHisBO);
        accountHisEntity.setAccountStatus(CommonStatusEnum.DEAL.getValue());
        accountHisDao.insert(accountHisEntity);
        return accountHisEntity;
    }

    private AccountHisEntity baseAccountHis(AccountInfoEntity account, AccountHisBO accountHisBO){
        AccountHisEntity accountHisEntity = new AccountHisEntity();
        accountHisEntity.setAmountFree(accountHisBO.getAmountFree());
        accountHisEntity.setMerchantOrderNo(accountHisBO.getMerchantOrderNo());
        accountHisEntity.setMerchantNo(account.getMerchantNo());
        accountHisEntity.setAmount(accountHisBO.getAmount());
        accountHisEntity.setAccountHisType(accountHisBO.getAccountHisType());
        accountHisEntity.setAccountNo(account.getAccountNo());
        accountHisEntity.setThirdNo(accountHisBO.getThirdNo());
        return accountHisEntity;
    }
}
