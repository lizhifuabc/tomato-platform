package com.tomato.sys.login.controller;

import com.tomato.domain.resp.SingleResp;
import com.tomato.sys.login.domain.req.LoginReq;
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
    @PostMapping("/login")
    public SingleResp login(@Valid @RequestBody LoginReq loginReq) {
        return SingleResp.buildSuccess();
    }
}
