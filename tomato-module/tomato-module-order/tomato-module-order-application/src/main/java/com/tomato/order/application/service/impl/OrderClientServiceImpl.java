package com.tomato.order.application.service.impl;

import com.tomato.order.application.req.OrderQueryByMerchantReq;
import com.tomato.order.application.req.OrderQueryResultReq;
import com.tomato.order.application.service.OrderClientService;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author lizhifu
 * @since 2023/8/1
 */
@Service
public class OrderClientServiceImpl implements OrderClientService {
    @Override
    public OrderQueryResultReq queryOrderMerchant(OrderQueryByMerchantReq orderQueryByMerchantDTO) {
        return null;
    }
}
