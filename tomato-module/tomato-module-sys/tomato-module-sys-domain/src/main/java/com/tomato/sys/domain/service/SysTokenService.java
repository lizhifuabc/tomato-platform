package com.tomato.sys.domain.service;

import com.tomato.sys.domain.entity.SysToken;

/**
 * token
 *
 * @author lizhifu
 * @since 2023/6/9
 */
public interface SysTokenService {

	/**
	 * 保存token
	 * @param username 用户名
	 * @param token token
	 * @param refreshToken refreshToken
	 */
	void saveToken(String username, String token, String refreshToken);

	/**
	 * 获取token
	 * @param username 用户名
	 * @return token
	 */
	SysToken getToken(String username);

}
