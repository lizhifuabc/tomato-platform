package com.tomato.notice.merchant.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户通知接收
 *
 * @author lizhifu
 * @since 2023/4/23
 */
@RestController
@Slf4j
public class MerchantReceiveNotice {
    @GetMapping("/receive")
    public String receive(){
        log.info("商户通知接收");
        return "success";
    }
}
