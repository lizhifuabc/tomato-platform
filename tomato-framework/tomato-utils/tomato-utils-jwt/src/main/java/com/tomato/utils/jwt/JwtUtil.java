package com.tomato.utils.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.PublicKey;
import java.util.Base64;

/**
 *
 * <p>
 * jwt 工具类 Token 签名验证辅助类
 * </p>
 *
 * @author lizhifu
 * @since 2023/8/28
 */
public class JwtUtil {

	/**
	 * 获取一个随机的 HS512 算法签名密钥
	 * @return 密钥
	 */
	public String getHS512SecretKey() {
		return getSecretKey(SignatureAlgorithm.HS512);
	}

	/**
	 * 获取对应签名算法的字符串密钥
	 * @param signatureAlgorithm 签名算法
	 * @return 密钥
	 */
	public String getSecretKey(SignatureAlgorithm signatureAlgorithm) {
		Key key = Keys.secretKeyFor(signatureAlgorithm);
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	/**
	 * 字符串密钥生成加密 Key
	 * @param signKey 密钥
	 * @param signatureAlgorithm 签名算法
	 * @return 加密 Key
	 */
	public SecretKey getSecretKey(String signKey, SignatureAlgorithm signatureAlgorithm) {
		return new SecretKeySpec(signKey.getBytes(), signatureAlgorithm.getJcaName());
	}

	/**
	 * 签名并生成 Token
	 * @param jwtBuilder jwtBuilder {@link io.jsonwebtoken.JwtBuilder}
	 * @param signKey 签名密钥（用于对称算法）
	 * @param signatureAlgorithm 签名算法
	 * @return Token
	 */
	public String sign(JwtBuilder jwtBuilder, String signKey, SignatureAlgorithm signatureAlgorithm) {
		// 普通签名
		SecretKey secretKey = getSecretKey(signKey, signatureAlgorithm);
		return jwtBuilder.signWith(secretKey, signatureAlgorithm).compact();
	}

	/**
	 * 签名并生成 Token
	 * @param jwtBuilder jwtBuilder {@link io.jsonwebtoken.JwtBuilder}
	 * @param rsaKey rsaKey {@link java.security.Key}
	 * @param signatureAlgorithm 签名算法
	 * @return Token
	 */
	public String signRsa(JwtBuilder jwtBuilder, Key rsaKey, SignatureAlgorithm signatureAlgorithm) {
		// RSA 签名
		return jwtBuilder.signWith(rsaKey, signatureAlgorithm).compact();
	}

	/**
	 * 验证签名并解析
	 * @param rsaPublicKey rsaPublicKey {@link java.security.PublicKey}
	 * @return JwtParser {@link io.jsonwebtoken.JwtParser}
	 */
	public JwtParser verifyParserRsa(PublicKey rsaPublicKey) {
		// RSA 签名验证
		return Jwts.parserBuilder().setSigningKey(rsaPublicKey).build();
	}

	/**
	 * 验证签名并解析
	 * @param signKey 签名密钥（用于对称算法）
	 * @param signatureAlgorithm 签名算法
	 * @return JwtParser {@link io.jsonwebtoken.JwtParser}
	 */
	public JwtParser verifyParser(String signKey, SignatureAlgorithm signatureAlgorithm) {
		// 普通签名验证
		SecretKey secretKey = getSecretKey(signKey, signatureAlgorithm);
		return Jwts.parserBuilder().setSigningKey(secretKey).build();
	}

}
