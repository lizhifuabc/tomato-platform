package com.tomato.goods;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedisTest
 *
 * @author lizhifu
 * @date 2022/12/10
 */
@SpringBootTest
public class RedisTest {
    @Resource
    RedisTemplate redisTemplate;

    @Test
    public void test(){

    }
}
