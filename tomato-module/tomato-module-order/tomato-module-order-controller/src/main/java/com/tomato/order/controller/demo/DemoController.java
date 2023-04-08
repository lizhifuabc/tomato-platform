package com.tomato.order.controller.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * demo
 *
 * @author lizhifu
 * @since 2023/4/8
 */
@RestController
public class DemoController {
    /**
     * demo
     * @return
     */
    @GetMapping("/demo")
    public String demo(String demo){
        return "SUCCESS"+demo;
    }
}
