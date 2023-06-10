package com.tomato.sys.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关
 *
 * @author lizhifu
 * @since 2023/6/8
 */
@RestController
@Tag(name = "用户相关", description = "用户相关 API")
public class SysUserController {
    @PostMapping("/sys/user/add")
    @PreAuthorize("hasAuthority('sys:user:add')")
    public void add() {

    }
}
