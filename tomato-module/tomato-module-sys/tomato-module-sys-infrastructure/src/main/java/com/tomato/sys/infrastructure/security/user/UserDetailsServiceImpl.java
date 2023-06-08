package com.tomato.sys.infrastructure.security.user;

import com.tomato.security.token.TokenService;
import com.tomato.sys.domain.entity.SysUser;
import com.tomato.sys.infrastructure.repository.SysUserJpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class UserDetailsServiceImpl implements UserDetailsService{
    private final TokenService tokenService;
    private final SysUserJpaRepository sysUserRepository;

    public UserDetailsServiceImpl(TokenService tokenService, SysUserJpaRepository sysUserRepository) {
        this.tokenService = tokenService;
        this.sysUserRepository = sysUserRepository;
    }
    /**
     * 当前用户
     * @return 当前用户
     */
    public SysUser currentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return sysUserRepository.findByUsername(username);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserRepository.findByUsername(username);
        return LoginUserDetails.builder()
                .authorities(buildAuthorities())
                .loginName(sysUser.getUsername())
                .build();
    }
    private Set<? extends GrantedAuthority> buildAuthorities() {
        HashSet<String> permissionList = new HashSet<>();
        permissionList.add("/login/logout");
        return permissionList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }
}
