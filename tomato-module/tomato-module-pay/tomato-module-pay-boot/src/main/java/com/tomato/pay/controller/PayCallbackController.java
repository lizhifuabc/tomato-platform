package com.tomato.pay.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 支付回调
 *
 * @author lizhifu
 * @since 2023/4/12
 */
@RestController
public class PayCallbackController {
    @PostMapping("/pay/callback")
    public void callback(@RequestParam Map<String, Object> requestParam) {

    }
}
