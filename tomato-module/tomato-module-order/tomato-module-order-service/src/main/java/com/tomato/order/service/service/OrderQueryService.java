package com.tomato.order.service.service;

import com.tomato.order.domain.domain.OrderQueryDomain;
import com.tomato.order.domain.domain.OrderQueryResultDomain;
import com.tomato.order.infrastructure.entity.OrderInfoEntity;
import com.tomato.order.infrastructure.mapper.OrderInfoEntityMapper;
import com.tomato.order.service.adapter.OrderServiceAdapter;
import org.springframework.stereotype.Service;

/**
 *  订单查询
 *
 * @author lizhifu
 * @date 2022/12/1
 */
@Service
public class OrderQueryService {
    private final OrderInfoEntityMapper orderInfoEntityMapper;
    private final OrderServiceAdapter orderServiceAdapter;
    public OrderQueryService(OrderInfoEntityMapper orderInfoEntityMapper, OrderServiceAdapter orderServiceAdapter) {
        this.orderInfoEntityMapper = orderInfoEntityMapper;
        this.orderServiceAdapter = orderServiceAdapter;
    }

    /**
     * 查询订单 TODO 返回 optional
     * @param orderQueryDomain
     * @return
     */
    public OrderQueryResultDomain queryOrderMerchant(OrderQueryDomain orderQueryDomain){
        OrderInfoEntity orderInfoEntity = orderInfoEntityMapper.selectByMerchant(orderQueryDomain.getMerchantNo(), orderQueryDomain.getMerchantOrderNo());
        return orderServiceAdapter.convert(orderInfoEntity);
    }
}
