package com.tomato.account.timer;

import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.enums.AccountStatusEnum;
import com.tomato.account.service.AccountOutReserveBalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
        log.info("账户风险预存期外余额定时定时执行start:[{}]", LocalDateTime.now());
        LocalDate exeLocalDate = LocalDate.now();
        // TODO 增加查询账户限制条件 TODO 分页
        accountInfoDao.selectAllAccount().forEach(accountNo ->{
            try {
                accountOutReserveBalanceService.exe(accountNo,exeLocalDate);
            }catch (Exception e){
                log.error("账户[{}]风险预存期外余额定时出现异常",accountNo,e);
            }
        });
        log.info("账户风险预存期外余额定时执行end:[{}]",LocalDateTime.now());
    }
}
