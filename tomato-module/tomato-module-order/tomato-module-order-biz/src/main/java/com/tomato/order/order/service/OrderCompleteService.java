package com.tomato.order.order.service;

import com.tomato.domain.core.exception.BusinessException;
import com.tomato.order.domain.constant.OrderStatusEnum;
import com.tomato.order.domain.entity.OrderEntity;
import com.tomato.order.order.dao.OrderDao;
import com.tomato.order.order.manager.OrderManager;
import org.springframework.stereotype.Service;

/**
 * 订单完成
 *
 * @author lizhifu
 * @since 2022/12/28
 */
@Service
public class OrderCompleteService {
    private final OrderManager orderManager;
    private final OrderDao orderDao;
    public OrderCompleteService(OrderManager orderManager, OrderDao orderDao) {
        this.orderManager = orderManager;
        this.orderDao = orderDao;
    }
    public void completeOrder(String orderNo, OrderStatusEnum orderStatusEnum){
        OrderEntity orderEntity = orderDao.selectByOrderNoBase(orderNo);
        int i = orderManager.completeOrder(orderNo, orderStatusEnum.getValue(), orderEntity.getVersion());
        if (i != 1){
            throw new BusinessException("订单已经是状态！");
        }
    }
}
