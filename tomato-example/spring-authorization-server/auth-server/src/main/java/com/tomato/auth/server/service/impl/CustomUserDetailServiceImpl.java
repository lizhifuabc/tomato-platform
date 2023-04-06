package com.tomato.auth.server.service.impl;

import com.tomato.auth.server.userdetails.CustomUser;
import com.tomato.auth.server.service.CustomUserDetailService;
import com.tomato.auth.server.sys.SysUserEntity;
import com.tomato.auth.server.sys.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 自定义 UserDetailService
 *
 * @author lizhifu
 * @since 2023/4/6
 */
@Service
@Slf4j
public class CustomUserDetailServiceImpl implements CustomUserDetailService {
    private final SysUserService sysUserService;

    public CustomUserDetailServiceImpl(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserServiceImpl loadUserByUsername username:{}",username);
        SysUserEntity sysUserEntity = sysUserService.selectByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username: [" + username + "] do not exist!"));
        // TODO 权限获取，初始修改密码
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("/hello");

        // TODO new CustomUser(sysUserEntity.getUsername(), sysUserEntity.getPassword(), sysUserEntity.getEnabled(), true, true, true, authorities);
        // 构造security用户
        return new CustomUser(sysUserEntity.getUsername(), sysUserEntity.getPassword(),authorities);
    }

    /**
     * 优先级
     * @return int
     */
    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
