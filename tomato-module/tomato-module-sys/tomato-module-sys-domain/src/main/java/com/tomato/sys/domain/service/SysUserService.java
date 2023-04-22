package com.tomato.sys.domain.service;

import com.tomato.sys.domain.entity.SysUser;

import java.util.Optional;

/**
 * 系统用户
 *
 * @author lizhifu
 * @since 2023/4/22
 */
public interface SysUserService {
    /**
     * 根据用户名查找SysUser
     *
     * @param userName 用户名
     * @return {@link SysUser}
     */
    public Optional<SysUser> getUserByUserName(String userName);
}
