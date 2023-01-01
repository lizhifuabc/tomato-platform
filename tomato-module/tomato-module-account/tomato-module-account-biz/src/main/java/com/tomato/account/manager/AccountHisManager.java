package com.tomato.account.manager;

import com.tomato.account.dao.AccountHisDao;
import com.tomato.account.domain.entity.AccountHisEntity;
import com.tomato.id.generator.impl.Sequence;
import org.springframework.stereotype.Service;

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

    public AccountHisEntity insert(AccountHisEntity accountHisEntity){
        accountHisEntity.setAccountHisId(sequence.nextId());
        accountHisDao.insert(accountHisEntity);
        return accountHisEntity;
    }
}
