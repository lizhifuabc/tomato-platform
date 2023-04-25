package com.tomato.notice.merchant.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 商户通知接收
 *
 * @author lizhifu
 * @since 2023/4/23
 */
@RestController
@Slf4j
public class MerchantReceiveNotice {
    @PostMapping("/receive")
    public String receive(@RequestBody Map<String, String> map, HttpServletRequest request){
        log.info("商户通知接收map:{}",map);
        // 随机睡眠 10 秒
        if(Math.random() > 0.5){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        // 随机返回失败
        if(Math.random() > 0.5){
            return "fail";
        }
        return "success";
    }
}
