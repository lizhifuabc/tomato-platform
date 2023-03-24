package com.tomato.seckill.manager;

import com.tomato.seckill.manager.cache.SeckillGoodsCache;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * HashSeckillGoodsCacheTest
 *
 * @author lizhifu
 * @since 2023/3/24
 */
@SpringBootTest
public class SeckillGoodsCacheTest {
    @Resource
    SeckillGoodsCache seckillGoodsCache;

    @Test
    public void test(){
        seckillGoodsCache.saveSeckillGoods(1L);
        System.out.println(seckillGoodsCache.getSeckillGoodsById(1L));
    }
}
