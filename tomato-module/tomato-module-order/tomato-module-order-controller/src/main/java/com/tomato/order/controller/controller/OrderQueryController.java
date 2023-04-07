package com.tomato.order.controller.controller;

import com.tomato.domain.resp.SingleResp;
import com.tomato.order.client.dto.OrderQueryDTO;
import com.tomato.order.client.dto.OrderQueryResultDTO;
import com.tomato.order.client.service.OrderClientService;
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
    public SingleResp<OrderQueryResultDTO> queryOrderMerchant(@Valid OrderQueryDTO orderQueryDTO) {
        return SingleResp.of(orderClientService.queryOrderMerchant(orderQueryDTO));
    }
}
