package com.tomato.account.manager;

import com.tomato.account.dao.AccountDao;
import com.tomato.account.domain.entity.AccountEntity;
import com.tomato.account.domain.req.AccountCreateReq;
import com.tomato.web.util.BeanUtil;
import org.springframework.stereotype.Service;

/**
 * 账户操作
 *
 * @author lizhifu
 * @since 2022/12/31
 */
@Service
public class AccountManager {
    private final AccountDao accountDao;
    private final AccountNoManager accountNoManager;
    public AccountManager(AccountDao accountDao, AccountNoManager accountNoManager) {
        this.accountDao = accountDao;
        this.accountNoManager = accountNoManager;
    }

    public void create(AccountCreateReq accountCreateReq) {
        AccountEntity accountEntity = BeanUtil.copy(accountCreateReq, AccountEntity.class);
        accountEntity.setAccountNo(accountNoManager.nextStringValue(accountCreateReq));
        accountDao.insert(accountEntity);
    }
}
