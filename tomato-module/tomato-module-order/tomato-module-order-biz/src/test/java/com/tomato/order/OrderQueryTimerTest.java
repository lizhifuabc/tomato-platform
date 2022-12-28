package com.tomato.order;

import com.tomato.order.timer.OrderQueryTimer;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * OrderQueryTimer
 *
 * @author lizhifu
 * @since 2022/12/28
 */
@SpringBootTest
public class OrderQueryTimerTest {
    @Resource
    OrderQueryTimer orderQueryTimer;

    @Test
    public void test(){
        orderQueryTimer.start();
    }
}
