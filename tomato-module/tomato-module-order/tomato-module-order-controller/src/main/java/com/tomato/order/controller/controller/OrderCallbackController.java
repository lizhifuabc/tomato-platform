package com.tomato.order.controller.controller;

import org.springframework.web.bind.annotation.RestController;

/**
 * 接收回调
 *
 * @author lizhifu
 * @since 2022/12/20
 */
@RestController
public class OrderCallbackController {
    /**
     * 模拟接收回调
     * @return
     */
    public String callback(String orderNo){

        return "SUCCESS";
    }
}
