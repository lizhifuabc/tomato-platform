package com.tomato.account.timer;

import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.enums.AccountStatusEnum;
import com.tomato.account.service.AccountOutReserveBalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 风险预存期外余额定时
 *
 * @author lizhifu
 * @since 2023/1/11
 */
@Component
@Slf4j
public class AccountOutReserveBalanceTimer {
    private final AccountOutReserveBalanceService accountOutReserveBalanceService;
    private final AccountInfoDao accountInfoDao;
    public AccountOutReserveBalanceTimer(AccountOutReserveBalanceService accountOutReserveBalanceService, AccountInfoDao accountInfoDao) {
        this.accountOutReserveBalanceService = accountOutReserveBalanceService;
        this.accountInfoDao = accountInfoDao;
    }
    /**
     * 每天凌晨1点执行一次
     */
    @Scheduled(cron="0 0 1 * * ?")
    public void run() {
        log.info("账户风险预存期外余额定时定时start");
        accountInfoDao.selectAllAccount().forEach(accountOutReserveBalanceService::exe);
        log.info("账户风险预存期外余额定时定时执行end");
    }
}
