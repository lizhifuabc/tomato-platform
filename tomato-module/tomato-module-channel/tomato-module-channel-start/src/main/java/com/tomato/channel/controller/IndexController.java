package com.tomato.channel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * IndexController
 *
 * @author lizhifu
 * @since 2023/8/17
 */
@RestController
@RequestMapping
public class IndexController {
    @RequestMapping("/")
    public String index(){
        return "hello";
    }
}
