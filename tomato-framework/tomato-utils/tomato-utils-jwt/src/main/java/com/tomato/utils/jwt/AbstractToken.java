package com.tomato.utils.jwt;


import lombok.Getter;

import java.time.Instant;

/**
 * 抽象 Token
 *
 * @author lizhifu
 * @since 2023/8/29
 */
@Getter
public abstract class AbstractToken {

	/**
	 * token 值
	 */
	private final String tokenValue;

	/**
	 * 发布时间
	 */
	private final Instant issuedAt;

	/**
	 * 过期时间
	 */
	private final Instant expiresAt;

	protected AbstractToken(String tokenValue) {
		this(tokenValue, null, null);
	}

	protected AbstractToken(String tokenValue, Instant issuedAt, Instant expiresAt) {
		this.tokenValue = tokenValue;
		this.issuedAt = issuedAt;
		this.expiresAt = expiresAt;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		AbstractToken other = (AbstractToken) obj;
		if (!this.getTokenValue().equals(other.getTokenValue())) {
			return false;
		}
		if ((this.getIssuedAt() != null) ? !this.getIssuedAt().equals(other.getIssuedAt())
				: other.getIssuedAt() != null) {
			return false;
		}
		return (this.getExpiresAt() != null) ? this.getExpiresAt().equals(other.getExpiresAt())
				: other.getExpiresAt() == null;
	}

	@Override
	public int hashCode() {
		int result = this.getTokenValue().hashCode();
		result = 31 * result + ((this.getIssuedAt() != null) ? this.getIssuedAt().hashCode() : 0);
		result = 31 * result + ((this.getExpiresAt() != null) ? this.getExpiresAt().hashCode() : 0);
		return result;
	}
}
