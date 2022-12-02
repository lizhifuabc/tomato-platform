package com.tomato.order;

import com.tomato.order.order.manager.OrderManager;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * OrderManager
 *
 * @author lizhifu
 * @date 2022/12/2
 */
@SpringBootTest
public class OrderManagerTest {
    @Resource
    OrderManager orderManager;

    @Test
    public void test(){
        System.out.println(orderManager.completeOrder("123", "123", 12));
    }
}
