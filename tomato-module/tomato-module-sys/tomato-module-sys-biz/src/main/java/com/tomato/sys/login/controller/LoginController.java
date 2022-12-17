package com.tomato.sys.login.controller;

import com.tomato.domain.resp.SingleResp;
import com.tomato.sys.login.domain.req.LoginReq;
import com.tomato.sys.login.domain.resp.LoginResp;
import com.tomato.sys.login.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
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
        SingleResp<LoginResp> singleResp = loginService.login(loginReq, "127.0.0.1", "user-agent");
        return singleResp;
    }
}
