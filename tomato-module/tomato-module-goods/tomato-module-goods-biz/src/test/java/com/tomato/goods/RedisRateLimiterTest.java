package com.tomato.goods;

import com.tomato.domain.resp.SingleResp;
import com.tomato.redis.domain.req.RedisRateLimiterReq;
import com.tomato.redis.domain.resp.RedisRateLimiterResp;
import com.tomato.redis.ratelimit.RedisRateLimiter;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * RedisRateLimiter
 *
 * @author lizhifu
 * @date 2022/12/10
 */
@SpringBootTest
public class RedisRateLimiterTest {
    @Resource
    RedisRateLimiter redisRateLimiter;

    @Test
    public void test(){
        RedisRateLimiterReq redisRateLimiterReq = RedisRateLimiterReq.builder()
                .id("test")
                .replenishRate(10)
                .burstCapacity(100)
                .requestedTokens(1)
                .build();
        SingleResp<RedisRateLimiterResp> allowed = redisRateLimiter.isAllowed(redisRateLimiterReq);
        System.out.println(allowed);
    }
}
