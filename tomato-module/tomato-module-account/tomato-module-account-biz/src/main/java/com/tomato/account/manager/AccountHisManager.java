package com.tomato.account.manager;

import com.tomato.account.dao.AccountHisDao;
import com.tomato.account.domain.entity.AccountHisEntity;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.req.AccountTradReq;
import com.tomato.domain.type.CommonStatusEnum;
import com.tomato.domain.type.YesNoTypeEnum;
import com.tomato.id.generator.impl.Sequence;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账户历史
 *
 * @author lizhifu
 * @since 2023/1/1
 */
@Service
public class AccountHisManager {
    private final Sequence sequence;
    private final AccountHisDao accountHisDao;

    public AccountHisManager(AccountHisDao accountHisDao) {
        this.sequence = new Sequence();
        this.accountHisDao = accountHisDao;
    }

    public AccountHisEntity insert(AccountInfoEntity account, AccountTradReq accountTradReq){
        AccountHisEntity accountHisEntity = new AccountHisEntity();
        accountHisEntity.setAmount(accountTradReq.getAmount());
        accountHisEntity.setAccountHisType(accountTradReq.getAccountHisType());
        accountHisEntity.setAccountNo(account.getAccountNo());
        accountHisEntity.setBeforeBalance(account.getBalance());
        accountHisEntity.setAfterBalance(account.getBalance().add(accountTradReq.getAmount()));
        accountHisEntity.setAccountHisSerial(account.getAccountHisSerial());
        accountHisEntity.setThirdNo(accountTradReq.getThirdNo());
        accountHisEntity.setFeeAmount(new BigDecimal(0));
        accountHisEntity.setAccountStatus(CommonStatusEnum.SUCCESS.getValue());
        accountHisEntity.setAllowSett(YesNoTypeEnum.YES.getValue());
        accountHisEntity.setCompleteTime(LocalDateTime.now());
        accountHisDao.insert(accountHisEntity);
        return accountHisEntity;
    }
    public AccountHisEntity insertAsync(AccountInfoEntity account, AccountTradReq accountTradReq){
        AccountHisEntity accountHisEntity = new AccountHisEntity();
        accountHisEntity.setAmount(accountTradReq.getAmount());
        accountHisEntity.setAccountHisType(accountTradReq.getAccountHisType());
        accountHisEntity.setAccountNo(account.getAccountNo());
        accountHisEntity.setThirdNo(accountTradReq.getThirdNo());
        accountHisEntity.setFeeAmount(new BigDecimal(0));
        accountHisEntity.setAccountStatus(CommonStatusEnum.DEAL.getValue());
        accountHisEntity.setAllowSett(YesNoTypeEnum.YES.getValue());
        accountHisDao.insert(accountHisEntity);
        return accountHisEntity;
    }
}
