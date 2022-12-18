package com.tomato.sys.login.controller;

import com.tomato.domain.resp.Resp;
import com.tomato.domain.resp.SingleResp;
import com.tomato.sys.domain.req.LoginReq;
import com.tomato.sys.domain.resp.LoginResp;
import com.tomato.sys.login.service.LoginService;
import com.tomato.web.util.servlet.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 登录
 *
 * @author lizhifu
 * @date 2022/12/12
 */
@RestController
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public SingleResp<LoginResp> login(@Valid @RequestBody LoginReq loginReq) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SingleResp<LoginResp> singleResp = loginService.login(loginReq, ServletUtil.getClientIP(request), "user-agent");
        return singleResp;
    }
    @GetMapping("/login/logout")
    public Resp logout(@RequestHeader(value = "TOKEN", required = false) String token) {
        return Resp.buildSuccess();
    }
}
