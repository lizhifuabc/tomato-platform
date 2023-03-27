package com.tomato.doc;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * RedisLockRegistry
 *
 * @author lizhifu
 * @since 2023/3/27
 */
@SpringBootTest
public class RedisLockRegistryTest {
    @Resource
    RedisLockRegistry redisLockRegistry;

    @Test
    public void test() throws InterruptedException {
        redisLockRegistry.obtain("test").lock();
        Thread.sleep(10000);
        redisLockRegistry.obtain("test").unlock();
    }
}
