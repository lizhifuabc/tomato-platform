package com.tomato.account.timer;

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
public class AccountTradAsyncTimerServiceTest {
    @Resource
    AccountTradAsyncTimerService accountTradAsyncTimerService;

    @Test
    public void test(){
        accountTradAsyncTimerService.exe("102023010703528952850");
        ThreadUtil.sleep(6000);
    }
}
