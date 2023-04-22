package com.tomato.sys.application.service.impl;

import com.tomato.sys.application.dto.SysLoginDTO;
import com.tomato.sys.application.service.SysUserLoginService;
import com.tomato.sys.domain.entity.SysUser;
import com.tomato.sys.domain.service.SysUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 登录
 *
 * @author lizhifu
 * @since 2023/4/22
 */
@Service
public class SysUserLoginServiceImpl implements SysUserLoginService {
    private final SysUserService sysUserService;

    public SysUserLoginServiceImpl(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public void login(SysLoginDTO sysLoginDTO) {
        // 根据用户名从数据库中查询到对应的用户实体对象，并进行密码验证。
        SysUser sysUser = sysUserService.getUserByUserName(sysLoginDTO.getUsername()).orElseThrow(() -> new RuntimeException("用户不存在"));
        // 调用 Infrastructure 层的 VerificationCodeService 进行验证码验证
    }
}
