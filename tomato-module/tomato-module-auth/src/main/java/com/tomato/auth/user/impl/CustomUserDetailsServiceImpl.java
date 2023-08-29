package com.tomato.auth.user.impl;

import com.tomato.auth.user.CustomUserDetailsService;
import com.tomato.auth.user.domain.CustomUserDetails;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 自定义 UserDetailsService 接口实现
 *
 * @author lizhifu
 * @since 2023/5/3
 */
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = User.withDefaultPasswordEncoder().username("admin").password("admin").roles("USER").build();
		return user;
	}

	@Override
	public CustomUserDetails loadCustomUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}

}
