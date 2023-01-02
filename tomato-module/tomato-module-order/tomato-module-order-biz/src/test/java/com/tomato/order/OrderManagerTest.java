package com.tomato.order;

import com.tomato.order.domain.entity.OrderEntity;
import com.tomato.order.order.manager.OrderManager;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

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
        System.out.println(orderManager.dealOrder("123", 12));
    }

    @Test
    public void create(){
        for (int i = 0; i < 100; i++) {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setClientIp("182.187.1.1");
            orderEntity.setRequestAmount(new BigDecimal(200));
            orderEntity.setOrderType(1);
            orderEntity.setPayType(1);
            orderEntity.setMerchantFee(new BigDecimal(10));
            orderEntity.setMerchantRate(new BigDecimal(100));

            orderEntity.setMerchantNo("123123121");
            orderEntity.setMerchantOrderNo(UUID.randomUUID().toString());
            orderEntity.setMerchantName("李志福而是");
            orderEntity.setNoticeSys("https://afadsf");
            orderEntity.setExtParam("fadsfasf");

            orderManager.createOrderDefault(orderEntity);
        }
    }
}
