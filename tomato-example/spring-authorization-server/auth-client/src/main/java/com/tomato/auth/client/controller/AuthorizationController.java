package com.tomato.auth.client.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 授权客户端
 *
 * @author lizhifu
 * @since 2023/4/5
 */
@RestController
public class AuthorizationController {
    @GetMapping("hello")
    public HttpEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }
}
