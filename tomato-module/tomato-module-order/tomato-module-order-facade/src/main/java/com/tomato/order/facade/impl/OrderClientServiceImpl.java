package com.tomato.order.facade.impl;

import com.tomato.order.client.dto.OrderQueryByMerchantDTO;
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
    public OrderQueryResultDTO queryOrderMerchant(OrderQueryByMerchantDTO orderQueryByMerchantDTO) {
        OrderQueryResultDomain orderQueryResultDomain = orderQueryService.queryOrderMerchant(orderFacadeAdapter.convertQuery(orderQueryByMerchantDTO));
        return orderFacadeAdapter.convertQuery(orderQueryResultDomain);
    }
}
