package com.tomato.account.manager;

import com.tomato.account.dao.AccountInfoDao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 风险预存期外
 *
 * @author lizhifu
 * @since 2023/4/20
 */
@Service
public class AccountOutReserveBalanceManager {
    private final AccountInfoDao accountInfoDao;

    public AccountOutReserveBalanceManager(AccountInfoDao accountInfoDao) {
        this.accountInfoDao = accountInfoDao;
    }

    public void updateOutReserveBalance(String accountNo, BigDecimal amount, Integer version, LocalDate exeLocalDate) {
        int update = accountInfoDao.updateOutReserveBalance(accountNo, amount, version, exeLocalDate);
        if (update != 1) {
            throw new RuntimeException("更新账户风险预存期外余额失败");
        }
    }
}
