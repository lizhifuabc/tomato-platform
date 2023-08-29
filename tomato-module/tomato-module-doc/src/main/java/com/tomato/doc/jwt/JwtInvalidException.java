package com.tomato.doc.jwt;

/**
 * TODO
 *
 * @author lizhifu
 * @since 2022/12/16
 */
public class JwtInvalidException extends RuntimeException {

	public JwtInvalidException(String message) {
		super(message);
	}

}
