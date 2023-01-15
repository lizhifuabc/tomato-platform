package com.tomato.account.timer;

import com.tomato.account.dao.AccountSettleControlDao;
import com.tomato.account.service.AccountSettleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * 账户结算定时
 * TODO 所有定时分布式锁，或者分布式定时中心
 * @author lizhifu
 * @since 2023/1/8
 */
@Component
@Slf4j
public class AccountSettleTimer {
    private final AccountSettleService accountSettleService;
    private final AccountSettleControlDao accountSettleControlDao;
    public AccountSettleTimer(AccountSettleService accountSettleService, AccountSettleControlDao accountSettleControlDao) {
        this.accountSettleService = accountSettleService;
        this.accountSettleControlDao = accountSettleControlDao;
    }
    /**
     * 每天凌晨2点执行一次
     */
    @Scheduled(cron="0 0 2 * * ?")
    public void run() {
        LocalDate nextSettleDate = LocalDate.now();
        log.info("账户结算定时，指定结算日期start:[{}]",nextSettleDate);
        accountSettleControlDao.selectAccount(nextSettleDate).forEach(accountNo->{
            try{
                accountSettleService.settle(nextSettleDate,accountNo);
            }catch (Exception e){
                log.error("账户[{}]结算定时出现异常",accountNo,e);
            }
        });
        log.info("账户结算定时，指定结算日期end:[{}]",nextSettleDate);
    }
}
