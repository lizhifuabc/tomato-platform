package com.tomato.auth.user;

import com.tomato.auth.user.domain.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 自定义 UserDetailsService 接口
 *
 * @author lizhifu
 * @since 2023/5/3
 */
public interface CustomUserDetailsService extends UserDetailsService {
    /**
     * 系统用户名
     *
     * @param username 用户账号
     * @return {@link CustomUserDetails}
     * @throws UsernameNotFoundException 用户不存在
     */
    CustomUserDetails loadCustomUserByUsername(String username) throws UsernameNotFoundException;
}
