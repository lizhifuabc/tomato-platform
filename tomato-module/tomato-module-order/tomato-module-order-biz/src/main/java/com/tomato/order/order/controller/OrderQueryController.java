package com.tomato.order.order.controller;

import com.tomato.domain.resp.SingleResp;
import com.tomato.order.domain.resp.OrderQueryResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
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
    @RequestMapping("/order/query")
    public SingleResp<OrderQueryResp> queryOrder(){
        OrderQueryResp orderQueryResp = OrderQueryResp.builder()
                .merchantName("上海人民中国股份有限公司上海人民中国股份有限公司")
                .build();
        log.info("OrderQueryController queryOrder return:{}",orderQueryResp);
        return SingleResp.of(orderQueryResp);
    }
}
