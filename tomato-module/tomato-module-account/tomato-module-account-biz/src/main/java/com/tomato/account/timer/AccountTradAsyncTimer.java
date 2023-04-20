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
        log.info("异步入账开始:{}", LocalDateTime.now());
        // 查询所有需要异步入账的账户
        List<String> accountList = accountAsyncInitService.accountList();
        // 执行异步入账
        for (String s : accountList) {
            log.info("异步入账[{}]",s);
            try {
                accountTradAsyncService.exe(s);
            } catch (Exception e) {
                log.error("异步入账[{}]异常",s, e);
            }
        }
        log.info("异步入账结束:{}", LocalDateTime.now());
    }
}
