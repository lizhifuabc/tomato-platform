package com.tomato.order.application.service;

import com.tomato.order.domain.repository.OrderInfoRepository;
import org.springframework.stereotype.Service;

/**
 * 订单新建
 *
 * @author lizhifu
 * @since  2022/12/1
 */
@Service
public class OrderCreateService {
    private final OrderInfoRepository orderInfoRepository;


    public OrderCreateService(OrderInfoRepository orderInfoRepository) {
        this.orderInfoRepository = orderInfoRepository;
    }
}
