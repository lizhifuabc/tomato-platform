package com.tomato.order.application.service;


import com.tomato.order.application.req.OrderQueryByMerchantReq;
import com.tomato.order.application.req.OrderQueryResultReq;

/**
 * TODO
 *
 * @author lizhifu
 * @since 2023/4/7
 */
public interface OrderClientService {
    OrderQueryResultReq queryOrderMerchant(OrderQueryByMerchantReq orderQueryByMerchantDTO);
}
