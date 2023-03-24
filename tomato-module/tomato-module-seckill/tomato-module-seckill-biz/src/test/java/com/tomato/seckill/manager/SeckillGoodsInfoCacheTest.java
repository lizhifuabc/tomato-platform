package com.tomato.seckill.manager;

import com.tomato.goods.domain.resp.GoodsInfoResp;
import com.tomato.seckill.manager.cache.SeckillGoodsInfoCache;
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
public class SeckillGoodsInfoCacheTest {
    @Resource
    SeckillGoodsInfoCache seckillGoodsInfoCache;

    @Test
    public void cache(){
        seckillGoodsInfoCache.saveSeckillGoodsInfo(1L);
        GoodsInfoResp seckillGoodsId = seckillGoodsInfoCache.getSeckillGoodsById(1L);
        System.out.println(seckillGoodsId);
    }
}
