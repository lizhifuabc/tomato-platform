package com.tomato.order.application.component;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * OrderNoComponentTest
 *
 * @author lizhifu
 * @since 2023/8/8
 */
@SpringBootTest
public class OrderNoComponentTest {
    @Resource
    OrderNoComponent orderNoComponent;

    /**
     * 生成订单编号
     */
     @Test
    public void createOrderNo() {
        System.out.println(orderNoComponent.createOrderNo("123456789"));
    }
}
