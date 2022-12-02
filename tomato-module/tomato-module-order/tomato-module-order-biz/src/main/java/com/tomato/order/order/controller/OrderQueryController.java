package com.tomato.order.order.controller;

import com.tomato.domain.resp.SingleResp;
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
public class OrderQueryController {
    @PostMapping("/order/query")
    public SingleResp queryOrder(){
        return SingleResp.of(null);
    }
}
