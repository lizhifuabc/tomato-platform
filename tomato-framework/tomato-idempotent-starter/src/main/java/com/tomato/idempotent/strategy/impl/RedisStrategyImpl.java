package com.tomato.idempotent.strategy.impl;

import com.tomato.domain.core.exception.BusinessException;
import com.tomato.idempotent.annotation.Idempotent;
import com.tomato.idempotent.strategy.IdempotentStrategy;
import org.aspectj.lang.JoinPoint;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;

/**
 * 幂等 Redis
 *
 * @author lizhifu
 * @since 2023/4/11
 */
@Service
public class RedisStrategyImpl implements IdempotentStrategy {
    private final StringRedisTemplate redisTemplate;
    public RedisStrategyImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void execute(JoinPoint joinPoint, Idempotent idempotent) {
        String methodName = joinPoint.getSignature().toString();
        String argsStr = Arrays.toString(joinPoint.getArgs());
        String redisKey = md5Encode(methodName + argsStr);
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(Objects.requireNonNull(redisKey), "", idempotent.timeout(), idempotent.timeUnit());
        if (Boolean.FALSE.equals(aBoolean)) {
            throw new BusinessException(idempotent.message());
        }
    }

    @Override
    public String name() {
        return "REDIS";
    }

    /**
     * MD5加密 TODO 方法集成到工具类
     *
     * @param string 字符串
     * @return String
     */
    public static String md5Encode(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                // 0xff 为 16 进制
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(md5Encode("中文"));
    }
}
