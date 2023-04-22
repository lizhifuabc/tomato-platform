package com.tomat.sys.application.service;

import com.tomato.sys.domain.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * 登录
 *
 * @author lizhifu
 * @since 2023/4/22
 */
@Service
public interface SysUserLoginService {
    /**
     * 登录
     *
     * @param userName 用户名
     * @param password 密码
     * @return {@link SysUserService}
     */
    public SysUserService login(String userName, String password);
}
