package com.tomato.account.manager;

import com.tomato.account.enums.AccountStatusEnum;
import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.domain.bo.AccountBalanceBO;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.req.AccountCreateReq;
import com.tomato.domain.exception.BusinessException;
import com.tomato.web.util.BeanUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
    /**
     * 查询账户
     *
     * @param accountNo 账户
     * @return
     */
    public AccountInfoEntity selectByAccountNo(String accountNo){
        return accountInfoDao.selectByAccountNo(accountNo);
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
    /**
     * 加钱
     * @param accountBalanceBO 账户金额操作
     * @param account 账户
     * @return 结果
     */
    public void add(AccountBalanceBO accountBalanceBO,AccountInfoEntity account){
        baseAccount(accountBalanceBO,account);
        int count = accountInfoDao.add(accountBalanceBO);
        if(count <= 0){
            throw new BusinessException("在账户加钱时候出现乐观锁异常");
        }
    }

    /**
     * 扣款
     * @param accountBalanceBO 账户金额操作
     * @param account 账户
     */
    public void deduct(AccountBalanceBO accountBalanceBO, AccountInfoEntity account) {
        baseAccount(accountBalanceBO,account);
        int count = accountInfoDao.deduct(accountBalanceBO);
        if(count <= 0){
            throw new BusinessException("在账户扣款时候出现乐观锁异常");
        }
    }

    private void baseAccount(AccountBalanceBO accountBalanceBO,AccountInfoEntity account){
        // lastTradTime : 2023年01月07日21:32:54
        LocalDateTime lastTradTime = account.getLastTradTime();
        // now : 2023年01月07日21:33:03
        LocalDateTime now = LocalDateTime.now();
        if(lastTradTime == null || (lastTradTime.with(LocalTime.MAX)).isBefore(now)){
            accountBalanceBO.setYesterdayBalance(account.getBalance());
            accountBalanceBO.setLastTradTime(now);
        }
        account.setAccountHisSerial(account.getAccountHisSerial() + 1);
    }
}
