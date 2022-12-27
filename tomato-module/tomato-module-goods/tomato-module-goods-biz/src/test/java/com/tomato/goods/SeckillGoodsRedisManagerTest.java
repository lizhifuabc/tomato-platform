package com.tomato.goods;

import com.tomato.goods.seckill.manager.SeckillGoodsRedisManager;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SeckillGoodsRedisService
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@SpringBootTest
public class SeckillGoodsRedisManagerTest {
    @Resource
    SeckillGoodsRedisManager seckillGoodsRedisManager;

    @Test
    public void test(){
        seckillGoodsRedisManager.resetSeckillActivity(121L);
        System.out.println(seckillGoodsRedisManager.resetSeckillGoods(121L));
        System.out.println(seckillGoodsRedisManager.leftPush(121L, 121L));
        System.out.println(seckillGoodsRedisManager.seckillRemaining(121L, 121L));
    }
    @Test
    public void remove(){
        seckillGoodsRedisManager.resetSeckillActivity(1L);
        for (int i = 0; i < 10; i++) {
            System.out.println(seckillGoodsRedisManager.deductSeckillGoods(1L, 1L));
        }
    }
}
