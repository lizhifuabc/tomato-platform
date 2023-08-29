package com.tomato.sys.infrastructure.repository;

import com.tomato.jpa.domain.repository.BaseJpaRepository;
import com.tomato.sys.domain.entity.SysUser;

import java.util.Optional;

/**
 * SysUserRepository
 *
 * @author lizhifu
 */
public interface SysUserRepository extends BaseJpaRepository<SysUser, String> {

	/**
	 * 根据用户名查找SysUser
	 * @param username 用户名
	 * @return {@link SysUser}
	 */
	Optional<SysUser> findByUsername(String username);

	/**
	 * 根据用户ID查找用户
	 * @param userId 用户ID
	 * @return {@link SysUser}
	 */
	SysUser findByUserId(Long userId);

}
