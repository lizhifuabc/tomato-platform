package com.tomato.seckill.service.cache;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 秒杀活动缓存
 *
 * @author lizhifu
 * @since 2023/3/17
 */
@SpringBootTest
public class SeckillCacheServiceTest {
    @Resource
    private SeckillCacheService seckillCacheService;

    // 缓存预热
    @Test
    public void cacheWarmUp() throws InterruptedException {
        seckillCacheService.cacheWarmUp(1L);
        seckillCacheService.cacheWarmUp(2L);
        Thread.sleep(1000);
    }
}
