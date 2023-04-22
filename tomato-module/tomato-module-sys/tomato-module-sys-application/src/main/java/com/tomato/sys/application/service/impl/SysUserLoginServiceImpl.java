package com.tomato.sys.application.service.impl;

import com.tomato.sys.application.dto.SysLoginDTO;
import com.tomato.sys.application.service.SysUserLoginService;
import com.tomato.sys.domain.entity.SysUser;
import com.tomato.sys.domain.service.SysUserService;
import com.tomato.sys.infrastructure.security.user.LoginUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * 登录
 *
 * @author lizhifu
 * @since 2023/4/22
 */
@Service
public class SysUserLoginServiceImpl implements SysUserLoginService {
    private final SysUserService sysUserService;
    private final AuthenticationManager authenticationManager;
    public SysUserLoginServiceImpl(SysUserService sysUserService, AuthenticationManager authenticationManager) {
        this.sysUserService = sysUserService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void login(SysLoginDTO sysLoginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(sysLoginDTO.getUsername(), sysLoginDTO.getPassword()));
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();
        // 根据用户名从数据库中查询到对应的用户实体对象，并进行密码验证。
        SysUser sysUser = sysUserService.getUserByUserName(sysLoginDTO.getUsername()).orElseThrow(() -> new RuntimeException("用户不存在"));
        // 调用 Infrastructure 层的 VerificationCodeService 进行验证码验证
    }
}
