package com.tomato.order.controller.controller;

import com.tomato.order.application.req.OrderCallbackReq;
import com.tomato.order.application.service.OrderCallbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 接收回调
 *
 * @author lizhifu
 * @since 2022/12/20
 */
@RestController
@RequestMapping
@Tag(name = "订单接口", description = "订单接口")
public class OrderCallbackController {
    private final OrderCallbackService orderCallbackService;

    public OrderCallbackController(OrderCallbackService orderCallbackService) {
        this.orderCallbackService = orderCallbackService;
    }

    /**
     * 订单接收回调，支付成功之后回调
     * @return String
     */
    @Operation(summary = "订单接收回调", description = "订单接收回调")
    @RequestMapping(value = "/order/callback",method = {RequestMethod.POST})
    public Map<String, String> callback(@RequestBody OrderCallbackReq orderCallbackReq){
        orderCallbackService.callback(orderCallbackReq);
        return response();
    }
    /**
     * 回调应答
     * @return response 应答
     */
    private Map<String, String> response() {
        Map<String, String> responseBody = new HashMap<>(2);
        responseBody.put("code", "SUCCESS");
        responseBody.put("message", "SUCCESS");
        return responseBody;
    }
}
