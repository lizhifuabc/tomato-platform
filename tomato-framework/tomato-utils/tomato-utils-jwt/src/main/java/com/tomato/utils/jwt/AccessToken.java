package com.tomato.utils.jwt;

import java.time.Instant;
import java.util.Collections;
import java.util.Set;

/**
 * access token
 *
 * @author lizhifu
 * @since 2023/8/29
 */
public class AccessToken extends AbstractToken {

	private final String tokenType = "Bearer";

	private final Set<String> scopes;

	public AccessToken(String tokenValue, Instant issuedAt, Instant expiresAt,
					   Set<String> scopes) {
		super(tokenValue, issuedAt, expiresAt);
		this.scopes = Collections.unmodifiableSet((scopes != null) ? scopes : Collections.emptySet());
	}

	public String getTokenType() {
		return tokenType;
	}

	public Set<String> getScopes() {
		return scopes;
	}
}
