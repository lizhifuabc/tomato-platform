package com.tomato.order.application.service;

import com.tomato.order.application.req.OrderCallbackReq;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * OrderCallbackService
 *
 * @author lizhifu
 * @since 2023/8/12
 */
@SpringBootTest
public class OrderCallbackServiceTest {
    @Resource
    private OrderCallbackService orderCallbackService;

    @Test
    public void callback() {
        OrderCallbackReq orderCallbackReq = new OrderCallbackReq();
        orderCallbackReq.setOrderNo("54044858177482756001001");
        orderCallbackService.callback(orderCallbackReq);
    }
}
