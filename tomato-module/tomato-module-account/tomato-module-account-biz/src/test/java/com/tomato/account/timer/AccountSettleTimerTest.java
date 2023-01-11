package com.tomato.account.timer;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * AccountSettleTimer
 *
 * @author lizhifu
 * @since 2023/1/11
 */
@SpringBootTest
public class AccountSettleTimerTest {
    @Resource
    AccountSettleTimer accountSettleTimer;
    @Resource
    AccountOutReserveBalanceTimer accountOutReserveBalanceTimer;
    @Test
    public void test(){
        accountSettleTimer.run();
    }
}
