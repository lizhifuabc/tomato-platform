package com.tomato.utils.jwt;

import java.time.Instant;

/**
 * refresh token
 *
 * @author lizhifu
 * @since 2023/8/29
 */
public class RefreshToken extends AbstractToken{

	protected RefreshToken(String tokenValue, Instant issuedAt) {
		this(tokenValue, issuedAt, null);
	}

	public RefreshToken(String tokenValue, Instant issuedAt, Instant expiresAt) {
		super(tokenValue, issuedAt, expiresAt);
	}
}
