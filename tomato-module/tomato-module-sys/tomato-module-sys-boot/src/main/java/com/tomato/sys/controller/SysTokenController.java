package com.tomato.sys.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TOKEN
 *
 * @author lizhifu
 * @since 2023/6/8
 */
@RestController
@Tag(name = "TOKEN", description = "TOKEN API")
public class SysTokenController {
    @PostMapping("/token/refresh-token")
    public void refreshToken() {

    }
}
