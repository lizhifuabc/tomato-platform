package com.tomato.sys.application.service.impl;

import com.tomato.sys.application.dto.SysLoginDTO;
import com.tomato.sys.application.service.SysUserLoginService;
import com.tomato.sys.domain.entity.SysUser;
import com.tomato.sys.domain.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private final SysUserService sysUserService;
    private final AuthenticationManager authenticationManager;
    public SysUserLoginServiceImpl(SysUserService sysUserService, AuthenticationManager authenticationManager) {
        this.sysUserService = sysUserService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void login(SysLoginDTO sysLoginDTO) {
        log.info("登录:{}",sysLoginDTO);
        // 调用 Spring Security 的 AuthenticationManager 进行用户名密码验证
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sysLoginDTO.getUsername(),sysLoginDTO.getPassword()));
        // 生成access_token 和 refresh_token TODO

    }
}
