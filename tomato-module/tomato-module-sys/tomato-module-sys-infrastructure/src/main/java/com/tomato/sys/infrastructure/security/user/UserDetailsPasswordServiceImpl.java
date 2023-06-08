package com.tomato.sys.infrastructure.security.user;

import com.tomato.sys.domain.entity.SysUser;
import com.tomato.sys.infrastructure.repository.SysUserJpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户密码更新
 *
 * @author lizhifu
 * @since 2023/4/23
 */
@Service
public class UserDetailsPasswordServiceImpl implements UserDetailsPasswordService {
    private final SysUserJpaRepository sysUserRepository;

    public UserDetailsPasswordServiceImpl(SysUserJpaRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    /**
     * 更新密码
     * @param user 用户
     * @param newPassword 新密码
     * @return UserDetails
     */
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        final SysUser sysUser = sysUserRepository.findByUserName(user.getUsername());
        sysUser.setPassword(newPassword);
        sysUserRepository.findByUserId(sysUser.getUserId());
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
