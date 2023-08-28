package com.tomato.utils.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultJwtBuilder;

/**
 * JwtUtil
 *
 * @author lizhifu
 * @since 2023/8/28
 */
public class JwtUtilTest {
    public static void main(String[] args) {
        JwtUtil jwtUtil = new JwtUtil();
        String signKey = jwtUtil.getHS512SecretKey();
        System.out.println(signKey);

        JwtBuilder jwtBuilder = new DefaultJwtBuilder();
        jwtBuilder.setSubject("tomato");
        jwtBuilder.setIssuer("tomato");
        jwtBuilder.setAudience("tomato");
        jwtBuilder.setExpiration(null);
        jwtBuilder.setNotBefore(null);
        jwtBuilder.setIssuedAt(null);
        jwtBuilder.setId(null);
        jwtBuilder.claim("tomato", "tomato");
        String sign = jwtUtil.sign(jwtBuilder, signKey, SignatureAlgorithm.HS512);
        System.out.println(sign);
        JwtParser jwtParser = jwtUtil.verifyParser(signKey, SignatureAlgorithm.HS512);
        System.out.println(jwtParser.parse(sign).getBody());
    }
}