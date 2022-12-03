package com.tomato.order.order.manager;

import com.tomato.order.order.dao.OrderDao;
import com.tomato.order.order.domain.bo.UpdateOrderStatusBO;
import constant.OrderStatusEnum;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 订单 manager
 *
 * @author lizhifu
 * @date 2022/12/2
 */
@Service
public class OrderManager {
    private final OrderDao orderDao;

    public OrderManager(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * 完成订单：只能从 DEAL-->终态
     * @param orderNo 订单号
     * @param orderStatus 终态：支付成功、支付失败、支付撤销、订单关闭
     * @param currentVersion 当前版本号
     * @return
     */
    public int completeOrder(String orderNo,String orderStatus,Integer currentVersion){
        UpdateOrderStatusBO updateOrderStatusBO = UpdateOrderStatusBO.builder()
                .orderNo(orderNo)
                .expectOrderStatus(OrderStatusEnum.DEAL.getValue())
                .completeTime(LocalDateTime.now())
                .orderStatus(orderStatus)
                .currentVersion(currentVersion)
                .build();
        return orderDao.updateOrderStatus(updateOrderStatusBO);
    }
    /**
     * 设置订单为支付中：只能从 INIT-->DEAL
     * @param orderNo 订单号
     * @param currentVersion 当前版本号
     * @return
     */
    public int dealOrder(String orderNo,Integer currentVersion){
        UpdateOrderStatusBO updateOrderStatusBO = UpdateOrderStatusBO.builder()
                .orderNo(orderNo)
                .expectOrderStatus(OrderStatusEnum.INIT.getValue())
                .orderStatus(OrderStatusEnum.DEAL.getValue())
                .currentVersion(currentVersion)
                .build();
        return orderDao.updateOrderStatus(updateOrderStatusBO);
    }
}
