package com.tomato.utils.jwt;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * access token 返回
 *
 * @author lizhifu
 * @since 2023/8/29
 */
@Getter
@Setter
public class AccessTokenResponse {

	/**
	 * access token
	 */
	private AccessToken accessToken;

	/**
	 * refresh token
	 */
	private RefreshToken refreshToken;

	/**
	 * 额外参数
	 */
	private Map<String, Object> additionalParameters;
}
