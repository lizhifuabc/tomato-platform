package com.tomato.sys.infrastructure.impl;

import com.tomato.sys.domain.entity.SysUser;
import com.tomato.sys.domain.service.SysUserService;
import com.tomato.sys.infrastructure.repository.SysUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 系统用户
 *
 * @author lizhifu
 * @since 2023/4/22
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    private final SysUserRepository sysUserRepository;

    public SysUserServiceImpl(SysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    @Override
    public Optional<SysUser> getUserByUserName(String userName) {
        return sysUserRepository.findByUsername(userName);
    }
}
