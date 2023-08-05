package com.tomato.order.application.service;

import com.tomato.order.application.req.OrderQueryByMerchantReq;
import com.tomato.order.application.req.OrderQueryByOrderNoReq;
import com.tomato.order.application.req.OrderQueryResultResp;
import com.tomato.order.domain.domain.entity.OrderInfoEntity;
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

    public OrderQueryResultResp orderQueryByMerchant(OrderQueryByMerchantReq orderQueryByMerchantReq) {
        OrderInfoEntity orderInfoEntity = orderInfoRepository.selectByMerchant(orderQueryByMerchantReq.getMerchantNo(),orderQueryByMerchantReq.getMerchantOrderNo());
        return null;
    }

    public OrderQueryResultResp orderQueryByOrderNo(OrderQueryByOrderNoReq orderQueryByOrderNoReq) {
        OrderInfoEntity orderInfoEntity = orderInfoRepository.selectByOrderNo(orderQueryByOrderNoReq.getMerchantNo(),orderQueryByOrderNoReq.getOrderNo());
        return null;
    }
}
