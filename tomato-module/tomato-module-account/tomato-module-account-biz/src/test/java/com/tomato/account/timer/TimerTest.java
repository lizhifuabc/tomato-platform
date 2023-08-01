package com.tomato.account.timer;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * TimerTest
 *
 * @author lizhifu
 * @since 2023/1/13
 */
@SpringBootTest
public class TimerTest {
    @Resource
    AccountSettleTimer accountSettleTimer;
    @Resource
    AccountOutReserveBalanceTimer accountOutReserveBalanceTimer;
    @Resource
    AccountTradAsyncTimer accountTradAsyncTimer;

    @Test
    public void timer(){
        System.out.println("执行所有定时start");
        accountTradAsyncTimer.run();
        accountOutReserveBalanceTimer.run();
        accountSettleTimer.run();
        System.out.println("执行所有定时end");
    }
}
