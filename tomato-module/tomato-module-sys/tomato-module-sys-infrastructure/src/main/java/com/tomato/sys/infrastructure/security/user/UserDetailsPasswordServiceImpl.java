package com.tomato.sys.infrastructure.security.user;

import com.tomato.sys.domain.entity.SysUser;
import com.tomato.sys.infrastructure.repository.SysUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * 用户密码更新
 *
 * @author lizhifu
 * @since 2023/4/23
 */
@Service
public class UserDetailsPasswordServiceImpl implements UserDetailsPasswordService {
    private final SysUserRepository sysUserRepository;

    public UserDetailsPasswordServiceImpl(SysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    /**
     * 更新密码,当用户登录的时候，回去自动检查当前用户密码是不是bcrypt，如果不是，则会自动进行密码升级，那么就会调用这个方法
     * @param user 用户
     * @param newPassword 新密码
     * @return UserDetails
     */
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        final SysUser sysUser = sysUserRepository.findByUsername(user.getUsername()).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        sysUser.setPassword(newPassword);
        sysUserRepository.save(sysUser);
        return new SecurityUserDetails(sysUser);
    }
}
