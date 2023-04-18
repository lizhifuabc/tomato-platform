package com.tomato.account.manager;

import com.tomato.account.dao.AccountRateDao;
import com.tomato.account.domain.entity.AccountRateEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * 账户费率
 *
 * @author lizhifu
 * @since 2023/4/18
 */
@Service
public class AccountRateManager {
    private final AccountRateDao accountRateDao;

    public AccountRateManager(AccountRateDao accountRateDao) {
        this.accountRateDao = accountRateDao;
    }

    public Optional<AccountRateEntity> getRate(String accountNo, String rateType) {
        return Optional.ofNullable(accountRateDao.selectByAccountNo(accountNo, rateType));
    }
}
