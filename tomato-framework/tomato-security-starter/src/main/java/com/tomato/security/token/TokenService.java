package com.tomato.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * token 服务
 *
 * @author lizhifu
 */
@Component
@Slf4j
public class TokenService {
    private static final long HOUR_TIME_MILLI = 60 * 60 * 1000;

    @Value("${tomato.token.key}")
    private String tokenKey;

    @Value("${tomato.token.expire-day}")
    private Integer tokenExpire;
    /**
     * 生成Token，并存入redis
     *
     * @param userId id
     * @param userName name
     * @param loginDeviceEnum 登录设备
     * @return token
     */
    public String generateToken(Long userId, String userName, LoginDeviceEnum loginDeviceEnum) {
        long nowTimeMilli = System.currentTimeMillis();

        Claims jwtClaims = Jwts.claims();
        jwtClaims.put(JwtConst.CLAIM_ID_KEY, userId);
        jwtClaims.put(JwtConst.CLAIM_NAME_KEY, userName);
        jwtClaims.put(JwtConst.CLAIM_DEVICE_KEY, loginDeviceEnum.getValue());

        Key key = Keys.hmacShaKeyFor(tokenKey.getBytes());

        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(jwtClaims)
                .setIssuedAt(new Date(nowTimeMilli))
                .setExpiration(new Date(nowTimeMilli + tokenExpire * 24 * HOUR_TIME_MILLI))
                .signWith(key);

        return jwtBuilder.compact();
    }
    /**
     * 解析并校验token信息 获取 userId
     *
     * @param token token
     * @return userId
     */
    public Long getUserId(String token) {
        Map<String, Object> parseJwtData = this.decryptToken(token);
        assert parseJwtData != null;
        return Long.valueOf(parseJwtData.get(JwtConst.CLAIM_ID_KEY).toString());
    }

    /**
     * 解密和解析token
     *
     * @param token token
     * @return body
     */
    public Map<String, Object> decryptToken(String token) {
        Key key = Keys.hmacShaKeyFor(tokenKey.getBytes());
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}