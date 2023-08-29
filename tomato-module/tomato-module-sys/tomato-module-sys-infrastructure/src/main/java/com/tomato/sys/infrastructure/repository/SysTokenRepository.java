package com.tomato.sys.infrastructure.repository;

import com.tomato.jpa.domain.repository.BaseJpaRepository;
import com.tomato.sys.domain.entity.SysToken;
import com.tomato.sys.domain.entity.SysUser;

import java.util.Optional;

/**
 * token repository
 *
 * @author lizhifu
 * @since 2023/6/9
 */
public interface SysTokenRepository extends BaseJpaRepository<SysToken, Long> {

	/**
	 * 根据用户id查询
	 * @param sysUser 用户
	 * @return SysToken token
	 */
	public Optional<SysToken> findBySysUser(SysUser sysUser);

	/**
	 * 根据用户id删除
	 * @param sysUser 用户
	 */
	void deleteBySysUser(SysUser sysUser);

}
