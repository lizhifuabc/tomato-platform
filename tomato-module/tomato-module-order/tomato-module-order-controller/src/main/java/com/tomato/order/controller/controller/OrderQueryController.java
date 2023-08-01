package com.tomato.order.controller.controller;

import com.tomato.common.resp.Resp;
import com.tomato.order.application.req.OrderQueryByMerchantReq;
import com.tomato.order.application.req.OrderQueryByOrderNoReq;
import com.tomato.order.application.req.OrderQueryResultReq;
import com.tomato.order.application.service.OrderClientService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单查询
 *
 * @author lizhifu
 * @since  2022/12/2
 */
@RestController
@Slf4j
public class OrderQueryController {
    private final OrderClientService orderClientService;

    public OrderQueryController(OrderClientService orderClientService) {
        this.orderClientService = orderClientService;
    }
    @RequestMapping("/order/query/merchant")
    public Resp<OrderQueryResultReq> queryOrderMerchant(@Valid OrderQueryByMerchantReq orderQueryByMerchantDTO) {
        return Resp.of(orderClientService.queryOrderMerchant(orderQueryByMerchantDTO));
    }
    @RequestMapping("/order/query/orderNo")
    public Resp<Void> queryOrderNo(@Valid OrderQueryByOrderNoReq orderQueryByMerchantDTO) {
        return Resp.buildSuccess();
    }
}
