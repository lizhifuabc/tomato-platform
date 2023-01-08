package com.tomato.account.timer;

import com.tomato.account.service.AccountTradAsyncService;
import com.tomato.util.thread.ThreadUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * AccountTradAsyncTimerService
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@SpringBootTest
public class AccountTradAsyncServiceTest {
    @Resource
    AccountTradAsyncService accountTradAsyncService;

    @Test
    public void test(){
        accountTradAsyncService.exe("102023010703528952850");
        ThreadUtil.sleep(6000);
    }
}
