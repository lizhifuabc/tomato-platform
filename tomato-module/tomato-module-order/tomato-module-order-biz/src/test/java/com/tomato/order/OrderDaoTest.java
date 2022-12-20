package com.tomato.order;

import com.tomato.order.order.dao.OrderDao;
import com.tomato.order.order.domain.bo.UpdateOrderStatusBO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * OrderDao
 *
 * @author lizhifu
 * @date 2022/12/2
 */
@SpringBootTest
public class OrderDaoTest {
    @Resource
    OrderDao orderDao;

    @Test
    public void test(){
        System.out.println(orderDao.selectByOrderNo("12"));
        System.out.println(orderDao.selectByMerchant("12", "12"));
        UpdateOrderStatusBO updateOrderStatusBO = UpdateOrderStatusBO.builder()
                .orderNo("12")
                .build();
        System.out.println(orderDao.updateOrderStatus(updateOrderStatusBO));
    }
}
