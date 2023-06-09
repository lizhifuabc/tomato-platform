package com.tomato.sys.application.service.impl;

import com.tomato.sys.application.dto.SysLoginDTO;
import com.tomato.sys.application.resp.SysLoginResp;
import com.tomato.sys.application.service.SysUserLoginService;
import com.tomato.sys.domain.service.SysTokenService;
import com.tomato.sys.domain.service.SysUserService;
import com.tomato.sys.infrastructure.security.config.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * 登录
 *
 * @author lizhifu
 * @since 2023/4/22
 */
@Service
@Slf4j
public class SysUserLoginServiceImpl implements SysUserLoginService {
    private final SysTokenService sysTokenService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public SysUserLoginServiceImpl(SysTokenService sysTokenService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.sysTokenService = sysTokenService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public SysLoginResp login(SysLoginDTO sysLoginDTO) {
        log.info("登录:{}",sysLoginDTO);
        // 调用 Spring Security 的 AuthenticationManager 进行用户名密码验证
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sysLoginDTO.getUsername(),sysLoginDTO.getPassword()));
        // 生成access_token 和 refresh_token 并返回
        User user = (User) authentication.getPrincipal();
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        sysTokenService.saveToken(user.getUsername(),jwtToken,refreshToken);
        return SysLoginResp.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }
}
