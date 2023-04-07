package com.tomato.order.client.service;

import com.tomato.order.client.dto.OrderQueryDTO;
import com.tomato.order.client.dto.OrderQueryResultDTO;

/**
 * TODO
 *
 * @author lizhifu
 * @since 2023/4/7
 */
public interface OrderClientService {
    OrderQueryResultDTO queryOrderMerchant(OrderQueryDTO orderQueryDTO);
}
