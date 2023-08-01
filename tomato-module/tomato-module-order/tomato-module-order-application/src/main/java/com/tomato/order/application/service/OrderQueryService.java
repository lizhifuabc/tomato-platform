package com.tomato.order.application.service;

import com.tomato.order.application.req.OrderQueryByMerchantReq;
import com.tomato.order.application.req.OrderQueryResultReq;
import com.tomato.order.domain.repository.OrderInfoRepository;
import org.springframework.stereotype.Service;

/**
 *  订单查询
 *
 * @author lizhifu
 * @since  2022/12/1
 */
@Service
public class OrderQueryService {
    private final OrderInfoRepository orderInfoRepository;

    public OrderQueryService(OrderInfoRepository orderInfoRepository) {
        this.orderInfoRepository = orderInfoRepository;
    }

    public OrderQueryResultReq queryOrderMerchant(OrderQueryByMerchantReq orderQueryByMerchantDTO) {
        return null;
    }
}
