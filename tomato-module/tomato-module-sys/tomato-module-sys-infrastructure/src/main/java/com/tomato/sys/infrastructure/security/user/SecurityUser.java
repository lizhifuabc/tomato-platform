package com.tomato.sys.infrastructure.security.user;

import com.tomato.sys.domain.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.stream.Stream;

/**
 * 安全用户信息
 *
 * @author lizhifu
 * @since 2023/6/8
 */
public class SecurityUser extends User {
    public SecurityUser(SysUser sysUser) {
        super(
                sysUser.getUsername(),
                sysUser.getPassword(),
                sysUser.isEnabled(),
                true,
                sysUser.getCredentialsExpireAt().isAfter(LocalDateTime.now()),
                true,
                sysUser.getRoles().stream().map(role -> role.getPermissions().stream().map(permission -> (GrantedAuthority) permission::getPermissionCode)).flatMap(Stream::distinct).toList()
        );
    }
}
