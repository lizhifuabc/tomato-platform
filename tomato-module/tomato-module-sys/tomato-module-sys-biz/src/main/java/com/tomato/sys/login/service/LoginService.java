package com.tomato.sys.login.service;

import com.tomato.domain.resp.SingleResp;
import com.tomato.security.token.LoginDeviceEnum;
import com.tomato.security.token.TokenService;
import com.tomato.sys.domain.entity.SysUserEntity;
import com.tomato.sys.domain.req.LoginReq;
import com.tomato.sys.domain.resp.LoginResp;
import com.tomato.sys.login.domain.bo.LoginUserDetails;
import com.tomato.sys.user.dao.SysUserDao;
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
    private final SysUserDao sysUserDao;
    public LoginService(TokenService tokenService, SysUserDao sysUserDao) {
        this.tokenService = tokenService;
        this.sysUserDao = sysUserDao;
    }

    public SingleResp<LoginResp> login(LoginReq loginReq, String ip, String userAgent) {
        String token = tokenService.generateToken(10000L,loginReq.getLoginName(), LoginDeviceEnum.PC);
        LoginResp loginResp = LoginResp.builder()
                .token(token)
                .build();
        return SingleResp.of(loginResp);
    }

    /**
     * 根据 token 获取用户信息
     *
     * @param token
     * @param request
     * @return
     */
    public UserDetails getLoginUserDetail(String token, HttpServletRequest request) {

        Long userId = tokenService.getUserId(token);
        SysUserEntity sysUserEntity = sysUserDao.selectById(userId);
        LoginUserDetails loginResp = LoginUserDetails.builder()
                .token(token)
                .loginName(sysUserEntity.getLoginName())
                .build();
        log.info("loginResp is {}",loginResp);
        return loginResp;
    }
}
