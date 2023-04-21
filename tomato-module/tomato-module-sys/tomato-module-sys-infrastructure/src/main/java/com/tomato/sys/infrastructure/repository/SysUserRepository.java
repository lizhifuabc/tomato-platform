package com.tomato.sys.infrastructure.repository;

import com.tomato.jpa.domain.repository.BaseRepository;
import com.tomato.sys.infrastructure.entity.SysUser;

/**
 * SysUserRepository
 * @author lizhifu
 */
public interface SysUserRepository extends BaseRepository<SysUser, String> {
    /**
     * 根据用户名查找SysUser
     *
     * @param userName 用户名
     * @return {@link SysUser}
     */
    SysUser findByUserName(String userName);

    /**
     * 根据用户ID查找用户
     *
     * @param userId 用户ID
     * @return {@link SysUser}
     */
    SysUser findByUserId(Long userId);
}
