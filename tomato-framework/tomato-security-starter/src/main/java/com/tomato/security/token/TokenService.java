package com.tomato.security.token;

import com.tomato.security.constant.JwtConstant;
import com.tomato.security.constant.RedisKeyConstant;
import com.tomato.security.enums.LoginDeviceEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * token 服务
 *
 * @author lizhifu
 */
@Slf4j
public class TokenService {
    private static final long HOUR_TIME_MILLI = 60 * 60 * 1000;
    private final StringRedisTemplate stringRedisTemplate;

    @Value("${tomato.token.key}")
    private String tokenKey;

    @Value("${tomato.token.expire-day}")
    private Integer tokenExpire;

    public TokenService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }
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
        jwtClaims.put(JwtConstant.CLAIM_ID_KEY, userId);
        jwtClaims.put(JwtConstant.CLAIM_NAME_KEY, userName);
        jwtClaims.put(JwtConstant.CLAIM_DEVICE_KEY, loginDeviceEnum.getValue());
        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(jwtClaims)
                .setIssuedAt(new Date(nowTimeMilli))
                .signWith(SignatureAlgorithm.HS512, tokenKey);

        jwtBuilder.setExpiration(new Date(nowTimeMilli + tokenExpire * 24 * HOUR_TIME_MILLI));
        String token = jwtBuilder.compact();
        String redisKey = redisKey(userId, loginDeviceEnum.getValue());
        stringRedisTemplate.opsForValue().set(redisKey, token, tokenExpire * 24 * 3600, TimeUnit.SECONDS);
        return token;
    }

    /**
     * redis key 生成
     * @param userId 用户id
     * @param device 设备
     * @return redis key
     */
    private String redisKey(Long userId, Integer device){
        return RedisKeyConstant.TOKEN + userId + ":" + device;
    }

    /**
     * 解析并校验token信息 获取 userId
     *
     * @param token token
     * @return userId
     */
    public Long getUserId(String token) {
        Map<String, Object> tokenData = this.decryptToken(token);
        boolean isValid = this.checkRedisToken(tokenData, token);
        if (!isValid) {
            return null;
        }
        return Long.valueOf(tokenData.get(JwtConstant.CLAIM_ID_KEY).toString());
    }
    /**
     * 删除用户所有token
     * @param userId 用户id
     */
    public void batchRemoveToken(Long userId) {
        for (LoginDeviceEnum device : LoginDeviceEnum.values()) {
            stringRedisTemplate.delete(this.redisKey(userId, device.getValue()));
        }
    }
    /**
     * 删除token
     * @param token token
     */
    public void removeToken(String token) {
        Map<String, Object> tokenData = this.decryptToken(token);
        boolean isValid = this.checkRedisToken(tokenData, token);
        // 校验通过才能删除
        if (!isValid) {
            return;
        }
        Long userId = Long.valueOf(tokenData.get(JwtConstant.CLAIM_ID_KEY).toString());
        Integer device = Integer.valueOf(tokenData.get(JwtConstant.CLAIM_DEVICE_KEY).toString());

        String redisKey = this.redisKey(userId, device);
        stringRedisTemplate.delete(redisKey);
    }
    /**
     * 校验token
     * @param token token
     * @return 校验token
     */
    public boolean checkRedisToken(String token) {
        Map<String, Object> tokenData = this.decryptToken(token);
        return this.checkRedisToken(tokenData, token);
    }
    /**
     * 校验token
     * @param tokenData 解析后的token
     * @param token token
     * @return 校验token
     */
    public boolean checkRedisToken(Map<String, Object> tokenData, String token) {
        Long userId = tokenData.get(JwtConstant.CLAIM_ID_KEY) == null ? null : Long.valueOf(tokenData.get(JwtConstant.CLAIM_ID_KEY).toString());
        Integer device = tokenData.get(JwtConstant.CLAIM_DEVICE_KEY) == null ? null : Integer.valueOf(tokenData.get(JwtConstant.CLAIM_DEVICE_KEY).toString());
        if (userId == null || device == null) {
            return false;
        }
        String redisKey = this.redisKey(userId, device);
        String redisToken = stringRedisTemplate.opsForValue().get(redisKey);
        return token.equals(redisToken);
    }
    /**
     * 解密和解析token
     *
     * @param token token
     * @return body
     */
    public Map<String, Object> decryptToken(String token) {
        return Jwts.parser()
                .setSigningKey(tokenKey)
                .parseClaimsJws(token)
                .getBody();
    }
}