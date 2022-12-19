package com.tomato.goods;

import com.tomato.goods.seckill.service.SeckillGoodsRedisService;
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
public class SeckillGoodsRedisServiceTest {
    @Resource
    SeckillGoodsRedisService seckillGoodsRedisService;

    @Test
    public void test(){
        seckillGoodsRedisService.resetSeckillActivity(121L);
        System.out.println(seckillGoodsRedisService.resetSeckillGoods(121L, 121L));
        System.out.println(seckillGoodsRedisService.leftPush(121L, 121L));
        System.out.println(seckillGoodsRedisService.seckillRemaining(121L, 121L));
    }
    @Test
    public void remove(){
        for (int i = 0; i < 10; i++) {
            System.out.println(seckillGoodsRedisService.rightPop(121L, 121L));
        }
    }
}
