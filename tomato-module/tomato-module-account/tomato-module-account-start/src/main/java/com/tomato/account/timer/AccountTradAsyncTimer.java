package com.tomato.account.timer;

import com.tomato.account.service.AccountAsyncInitService;
import com.tomato.account.service.AccountTradAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 账户异步入账服务定时
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Component
@Slf4j
public class AccountTradAsyncTimer {
    private final AccountAsyncInitService accountAsyncInitService;
    private final AccountTradAsyncService accountTradAsyncService;
    public AccountTradAsyncTimer(AccountAsyncInitService accountAsyncInitService, AccountTradAsyncService accountTradAsyncService) {
        this.accountAsyncInitService = accountAsyncInitService;
        this.accountTradAsyncService = accountTradAsyncService;
    }
    /**
     * 每分钟执行一次
     */
    @Scheduled(cron="0 0/1 * * * ?")
    public void run() {
        log.info("账户异步入账定时:{}", LocalDateTime.now());
        // 查询所有需要异步入账的账户
        List<String> accountList = accountAsyncInitService.accountList();
        // 执行异步入账
        for (String accountNo : accountList) {
            log.info("账户异步入账服务 accountNo:{}",accountNo);
            try {
                accountTradAsyncService.exe(accountNo);
            } catch (Exception e) {
                log.error("账户异步入账服务异常 accountNo:{}",accountNo,e);
            }
        }
    }
}
