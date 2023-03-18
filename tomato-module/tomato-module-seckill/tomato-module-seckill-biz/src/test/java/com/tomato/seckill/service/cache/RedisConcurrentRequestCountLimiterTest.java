package com.tomato.seckill.service.cache;

import com.tomato.redis.domain.req.RedisConcurrentRequestCountLimiterReq;
import com.tomato.redis.ratelimit.RedisConcurrentRequestCountLimiter;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * RedisConcurrentRequestCountLimiter
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@SpringBootTest
public class RedisConcurrentRequestCountLimiterTest {
    @Resource
    RedisConcurrentRequestCountLimiter redisConcurrentRequestCountLimiter;

    @SneakyThrows
    @Test
    public void test(){
        for (int i = 0; i < 5; i++) {
            RedisConcurrentRequestCountLimiterReq req = RedisConcurrentRequestCountLimiterReq.builder()
                    .id("10000")
                    .count(2)
                    .interval(5)
                    .build();
            System.out.println(redisConcurrentRequestCountLimiter.isAllowed(req));
            Thread.sleep(4000L);
        }
    }
}
