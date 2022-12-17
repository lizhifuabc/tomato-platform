package com.tomato.sys.login.service;

import com.tomato.domain.resp.SingleResp;
import com.tomato.security.token.LoginDeviceEnum;
import com.tomato.security.token.TokenService;
import com.tomato.sys.login.domain.req.LoginReq;
import com.tomato.sys.login.domain.resp.LoginResp;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * 登录服务
 *
 * @author lizhifu
 * @since 2022/12/16
 */
@Service
@Slf4j
public class LoginService {
    private final TokenService tokenService;

    public LoginService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public SingleResp<LoginResp> login(LoginReq loginReq, String ip, String userAgent) {
        String token = tokenService.generateToken(10000L,loginReq.getLoginName(), LoginDeviceEnum.PC);
        LoginResp loginResp = new LoginResp();
        loginResp.setToken(token);
        return SingleResp.of(loginResp);
    }

    public UserDetails getLoginUserDetail(String token, HttpServletRequest request) {
        log.info("token is {}",token);
        return null;
    }
}
