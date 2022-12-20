package com.tomato.order.order.controller;

import com.tomato.domain.resp.SingleResp;
import com.tomato.order.domain.req.OrderQueryMerchantReq;
import com.tomato.order.domain.req.OrderQueryOrderNoReq;
import com.tomato.order.domain.resp.OrderQueryResp;
import com.tomato.order.order.service.OrderQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单查询
 *
 * @author lizhifu
 * @date 2022/12/2
 */
@RestController
@Slf4j
public class OrderQueryController {
    private final OrderQueryService orderQueryService;

    public OrderQueryController(OrderQueryService orderQueryService) {
        this.orderQueryService = orderQueryService;
    }

    @RequestMapping("/order/query/merchant")
    public SingleResp<OrderQueryResp> queryOrderMerchant(@Validated @RequestBody OrderQueryMerchantReq req){
        return SingleResp.of(orderQueryService.queryOrderMerchant(req));
    }
    @RequestMapping("/order/query/orderNo")
    public SingleResp<OrderQueryResp> queryOrderOrderNo(@Validated @RequestBody OrderQueryOrderNoReq req){
        return SingleResp.of(orderQueryService.queryOrderOrderNo(req));
    }
}
