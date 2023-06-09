package com.tomato.sys.infrastructure.impl;

import com.tomato.sys.domain.entity.SysToken;
import com.tomato.sys.domain.entity.SysUser;
import com.tomato.sys.domain.enums.TokenType;
import com.tomato.sys.domain.service.SysTokenService;
import com.tomato.sys.infrastructure.repository.SysTokenRepository;
import com.tomato.sys.infrastructure.repository.SysUserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * token
 *
 * @author lizhifu
 * @since 2023/6/9
 */
@Service
public class SysTokenServiceImpl implements SysTokenService {
    private final SysTokenRepository sysTokenRepository;
    private final SysUserRepository sysUserRepository;
    public SysTokenServiceImpl(SysTokenRepository sysTokenRepository, SysUserRepository sysUserRepository) {
        this.sysTokenRepository = sysTokenRepository;
        this.sysUserRepository = sysUserRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveToken(String username,String token, String refreshToken) {
        SysUser sysUser = sysUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        SysToken sysToken = sysTokenRepository.findBySysUser(sysUser).orElse(new SysToken());
        sysToken.setSysUser(sysUser);
        sysToken.setExpired(false);
        sysToken.setRevoked(false);
        sysToken.setTokenType(TokenType.BEARER);
        sysToken.setToken(token);
        sysTokenRepository.save(sysToken);
    }
}
