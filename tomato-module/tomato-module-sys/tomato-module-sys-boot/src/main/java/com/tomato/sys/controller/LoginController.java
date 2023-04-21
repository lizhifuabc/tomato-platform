package com.tomato.sys.controller;

import com.tomato.common.resp.Resp;
import com.tomato.sys.form.LoginForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 登录
 *
 * @author lizhifu
 * @since  2022/12/12
 */
@RestController
@Tag(name = "登录相关", description = "登录相关")
public class LoginController {
    @PostMapping("/login")
    @Operation(summary = "登录", description = "登录")
    public Resp<Void> login(@Valid @RequestBody LoginForm loginForm) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return Resp.buildSuccess();
    }
}
