package com.tomato.order.service.service;

import com.tomato.order.infrastructure.mapper.OrderInfoEntityMapper;
import org.springframework.stereotype.Service;

/**
 * 订单新建
 *
 * @author lizhifu
 * @date 2022/12/1
 */
@Service
public class OrderCreateService {
    private final OrderInfoEntityMapper orderInfoEntityMapper;

    public OrderCreateService(OrderInfoEntityMapper orderInfoEntityMapper) {
        this.orderInfoEntityMapper = orderInfoEntityMapper;
    }
}
