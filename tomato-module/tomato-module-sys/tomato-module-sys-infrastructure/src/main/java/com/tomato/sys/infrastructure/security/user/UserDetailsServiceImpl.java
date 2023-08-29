package com.tomato.sys.infrastructure.security.user;

import com.tomato.sys.domain.entity.SysUser;
import com.tomato.sys.infrastructure.repository.SysUserRepository;
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
public class UserDetailsServiceImpl implements UserDetailsService {

	private final SysUserRepository sysUserRepository;

	public UserDetailsServiceImpl(SysUserRepository sysUserRepository) {
		this.sysUserRepository = sysUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser sysUser = sysUserRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
		return new SecurityUserDetails(sysUser);
	}

}
