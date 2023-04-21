package com.tomato.sys.login.controller;

import com.tomato.common.resp.Resp;
import com.tomato.security.constant.RequestHeaderConstant;
import com.tomato.sys.domain.req.LoginReq;
import com.tomato.sys.domain.resp.LoginResp;
import com.tomato.sys.login.service.LoginService;
import com.tomato.web.util.servlet.IpUtil;
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
    public Resp<LoginResp> login(@Valid @RequestBody LoginReq loginReq) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Resp<LoginResp> singleResp = loginService.login(loginReq, IpUtil.getClientIp(request));
        return singleResp;
    }
    @GetMapping("/login/logout")
    public Resp logout(@RequestHeader(value = RequestHeaderConstant.TOKEN, required = false) String token) {
        return Resp.buildSuccess();
    }
}
