package com.tomato.order.order.service;

import com.tomato.order.domain.req.OrderQueryMerchantReq;
import com.tomato.order.domain.req.OrderQueryOrderNoReq;
import com.tomato.order.domain.resp.OrderQueryResp;
import com.tomato.order.order.dao.OrderDao;
import org.springframework.stereotype.Service;

/**
 *  订单查询
 *
 * @author lizhifu
 * @date 2022/12/1
 */
@Service
public class OrderQueryService {
    private final OrderDao orderDao;

    public OrderQueryService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public OrderQueryResp queryOrderMerchant(OrderQueryMerchantReq req) {
        // TODO hmac
        return orderDao.selectByMerchant(req.getMerchantNo(),req.getMerchantOrderNo());
    }

    public OrderQueryResp queryOrderOrderNo(OrderQueryOrderNoReq req) {
        // TODO hmac
        return orderDao.selectByOrderNo(req.getOrderNo());
    }
}
