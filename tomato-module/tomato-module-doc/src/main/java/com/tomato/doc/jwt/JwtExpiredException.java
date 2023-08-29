package com.tomato.doc.jwt;

/**
 * TODO
 *
 * @author lizhifu
 * @since 2022/12/16
 */
public class JwtExpiredException extends RuntimeException {

	public JwtExpiredException(String message) {
		super(message);
	}

}
