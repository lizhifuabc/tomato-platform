package com.tomato.account.timer;

import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.service.AccountOutReserveBalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 风险预存期外余额定时
 *
 * @author lizhifu
 * @since 2023/1/11
 */
@Component
@Slf4j
public class AccountOutReserveBalanceTimer {
    /**
     * 查询数量
     */
    private static final int PAGE_SIZE = 100;
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
        // 当前页码
        int pageIndex = 0;
        LocalDate exeLocalDate = LocalDate.now();
        while (true){
            List<String> accountList = null;
            try {
                // TODO 是否增加账户状态筛选
                accountList = accountInfoDao.selectAllAccount(pageIndex, PAGE_SIZE);
            }catch (Exception e){
                log.error("账户风险预存期外余额定时定时执行出现异常",e);
            }

            // 查询无结果
            if (accountList == null || accountList.isEmpty()){
                break;
            }
            accountList.forEach(accountNo ->{
                try {
                    accountOutReserveBalanceService.exe(accountNo,exeLocalDate);
                }catch (Exception e){
                    log.error("账户[{}]风险预存期外余额定时出现异常",accountNo,e);
                }
            });
            pageIndex++;
        }
        log.info("账户风险预存期外余额定时执行end:[{}]",LocalDateTime.now());
    }
}
