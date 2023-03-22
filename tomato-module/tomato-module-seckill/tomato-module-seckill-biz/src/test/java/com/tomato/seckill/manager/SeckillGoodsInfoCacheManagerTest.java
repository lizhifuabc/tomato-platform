package com.tomato.seckill.manager;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SeckillGoodsInfoCacheManager
 *
 * @author lizhifu
 * @since 2023/3/22
 */
@SpringBootTest
public class SeckillGoodsInfoCacheManagerTest {
    @Resource
    SeckillGoodsInfoCacheManager seckillGoodsInfoCacheManager;

    @Test
    public void cache(){
        seckillGoodsInfoCacheManager.cache(1L);
    }
}
