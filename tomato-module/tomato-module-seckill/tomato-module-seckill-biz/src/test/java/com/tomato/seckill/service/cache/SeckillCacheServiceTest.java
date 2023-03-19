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
        for (int i = 0; i < 2; i++) {
            // 创建线程并运行
            new Thread(() -> {
                seckillCacheService.cacheWarmUp(1L);
            }).start();
            new Thread(() -> {
                seckillCacheService.cacheWarmUp(2L);
            }).start();
        }
        Thread.sleep(1000);
    }
}
