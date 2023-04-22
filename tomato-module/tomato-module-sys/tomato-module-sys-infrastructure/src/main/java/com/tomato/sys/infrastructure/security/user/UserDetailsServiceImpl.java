package com.tomato.sys.infrastructure.security.user;

import com.tomato.security.token.TokenService;
import com.tomato.sys.domain.entity.SysUser;
import com.tomato.sys.infrastructure.repository.SysUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户
 *
 * @author lizhifu
 * @since 2023/4/22
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final TokenService tokenService;
    private final SysUserRepository sysUserRepository;

    public UserDetailsServiceImpl(TokenService tokenService, SysUserRepository sysUserRepository) {
        this.tokenService = tokenService;
        this.sysUserRepository = sysUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserRepository.findByUserName(username);
        return LoginUserDetails.builder()
                .authorities(buildAuthorities())
                .loginName(sysUser.getUserName())
                .build();
    }
    private Set<? extends GrantedAuthority> buildAuthorities() {
        HashSet<String> permissionList = new HashSet<>();
        permissionList.add("/login/logout");
        return permissionList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }
}
