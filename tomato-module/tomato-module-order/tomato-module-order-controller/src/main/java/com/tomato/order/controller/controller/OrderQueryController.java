package com.tomato.order.controller.controller;

import com.tomato.common.resp.Resp;
import com.tomato.order.application.req.OrderQueryByMerchantReq;
import com.tomato.order.application.req.OrderQueryByOrderNoReq;
import com.tomato.order.application.req.OrderQueryResultResp;
import com.tomato.order.application.service.OrderQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "订单查询", description = "订单查询")
public class OrderQueryController {
    private final OrderQueryService orderQueryService;

    public OrderQueryController(OrderQueryService orderQueryService) {
        this.orderQueryService = orderQueryService;
    }

    @RequestMapping("/order/query/merchant")
    @Operation(summary = "商户号订单查询", description = "商户号订单查询")
    public Resp<OrderQueryResultResp> queryOrderMerchant(@Valid OrderQueryByMerchantReq orderQueryByMerchantDTO) {
        return Resp.of(orderQueryService.orderQueryByMerchant(orderQueryByMerchantDTO));
    }
    @RequestMapping("/order/query/orderNo")
    @Operation(summary = "订单号查询", description = "订单号查询")
    public Resp<OrderQueryResultResp> queryOrderNo(@Valid OrderQueryByOrderNoReq orderQueryByOrderNoReq) {
        return Resp.of(orderQueryService.orderQueryByOrderNo(orderQueryByOrderNoReq));
    }
}
