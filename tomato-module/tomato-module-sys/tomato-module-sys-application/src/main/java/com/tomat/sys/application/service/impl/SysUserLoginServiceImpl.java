package com.tomat.sys.application.service.impl;

import com.tomat.sys.application.service.SysUserLoginService;
import com.tomato.sys.domain.service.SysUserService;
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

    public SysUserLoginServiceImpl(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public SysUserService login(String userName, String password) {
        sysUserService.getUserByUserName(userName);
        return null;
    }
}
