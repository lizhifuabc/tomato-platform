package com.tomato.account.timer;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 风险预存期外余额定时
 *
 * @author lizhifu
 * @since 2023/1/11
 */
@SpringBootTest
public class AccountOutReserveBalanceTimerTest {
    @Resource
    AccountOutReserveBalanceTimer accountOutReserveBalanceTimer;

    @Test
    public void test(){
        accountOutReserveBalanceTimer.run();
    }
}
