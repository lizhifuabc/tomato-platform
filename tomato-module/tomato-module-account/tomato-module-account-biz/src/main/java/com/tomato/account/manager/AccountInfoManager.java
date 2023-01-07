package com.tomato.account.manager;

import com.tomato.account.constant.AccountStatusEnum;
import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.req.AccountCreateReq;
import com.tomato.domain.exception.BusinessException;
import com.tomato.web.util.BeanUtil;
import org.springframework.stereotype.Service;

/**
 * 账户操作
 *
 * @author lizhifu
 * @since 2022/12/31
 */
@Service
public class AccountInfoManager {
    private final AccountInfoDao accountInfoDao;
    private final AccountNoManager accountNoManager;
    public AccountInfoManager(AccountInfoDao accountInfoDao, AccountNoManager accountNoManager) {
        this.accountInfoDao = accountInfoDao;
        this.accountNoManager = accountNoManager;
    }

    public AccountInfoEntity create(AccountCreateReq accountCreateReq) {
        AccountInfoEntity accountInfoEntity = BeanUtil.copy(accountCreateReq, AccountInfoEntity.class);
        accountInfoEntity.setAccountNo(accountNoManager.nextStringValue(accountCreateReq));
        accountInfoEntity.setAccountStatus(AccountStatusEnum.ACCOUNT_AVAILABLE.getValue());
        accountInfoDao.insert(accountInfoEntity);
        return accountInfoEntity;
    }

    public void updateAccountStatus(String accountNo, String accountStatus, Integer version) {
        int count = accountInfoDao.updateAccountStatus(accountNo , accountStatus ,version);
        if(count <= 0){
            throw new BusinessException("在注销账户的时候出现乐观锁异常");
        }
    }
}
