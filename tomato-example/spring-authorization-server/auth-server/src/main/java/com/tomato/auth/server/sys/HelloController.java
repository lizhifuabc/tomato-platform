package com.tomato.auth.server.sys;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * hello
 *
 * @author lizhifu
 * @since 2023/4/4
 */
@RestController
public class HelloController {
    @GetMapping("hello")
    public HttpEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }
}
