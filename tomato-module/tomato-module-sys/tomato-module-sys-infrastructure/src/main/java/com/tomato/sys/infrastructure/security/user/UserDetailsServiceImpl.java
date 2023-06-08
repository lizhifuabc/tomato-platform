package com.tomato.sys.infrastructure.security.user;

import com.tomato.sys.domain.entity.SysUser;
import com.tomato.sys.infrastructure.repository.SysUserJpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户
 *
 * @author lizhifu
 * @since 2023/4/22
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    private final SysUserJpaRepository sysUserRepository;

    public UserDetailsServiceImpl(SysUserJpaRepository sysUserRepository) {
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
        return new SecurityUserDetails(sysUser);
    }
}
