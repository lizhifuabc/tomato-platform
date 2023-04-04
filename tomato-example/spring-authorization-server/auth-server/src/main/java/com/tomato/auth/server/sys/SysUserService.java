package com.tomato.auth.server.sys;

import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * SysUserService
 *
 * @author lizhifu
 * @since 2023/4/4
 */
@Service
public class SysUserService {
    private final SysUserEntityMapper sysUserEntityMapper;

    public SysUserService(SysUserEntityMapper sysUserEntityMapper) {
        this.sysUserEntityMapper = sysUserEntityMapper;
    }

    public Optional<SysUserEntity> selectByUsername(String username){
        return Optional.ofNullable(sysUserEntityMapper.selectByUsername(username));
    }
}
