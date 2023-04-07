package com.tomato.order.facade.impl;

import com.tomato.order.client.dto.OrderQueryDTO;
import com.tomato.order.client.dto.OrderQueryResultDTO;
import com.tomato.order.client.service.OrderClientService;
import com.tomato.order.domain.domain.OrderQueryResultDomain;
import com.tomato.order.facade.adapter.OrderFacadeAdapter;
import com.tomato.order.service.service.OrderQueryService;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author lizhifu
 * @since 2023/4/7
 */
@Service
public class OrderClientServiceImpl implements OrderClientService {
    private final OrderQueryService orderQueryService;
    private final OrderFacadeAdapter orderFacadeAdapter;
    public OrderClientServiceImpl(OrderQueryService orderQueryService, OrderFacadeAdapter orderFacadeAdapter) {
        this.orderQueryService = orderQueryService;
        this.orderFacadeAdapter = orderFacadeAdapter;
    }

    @Override
    public OrderQueryResultDTO queryOrderMerchant(OrderQueryDTO orderQueryDTO) {
        OrderQueryResultDomain orderQueryResultDomain = orderQueryService.queryOrderMerchant(orderFacadeAdapter.convertQuery(orderQueryDTO));
        return orderFacadeAdapter.convertQuery(orderQueryResultDomain);
    }
}
