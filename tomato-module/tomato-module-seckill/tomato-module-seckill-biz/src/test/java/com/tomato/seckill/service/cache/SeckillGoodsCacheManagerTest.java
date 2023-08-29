package com.tomato.seckill.service.cache;

import com.tomato.seckill.manager.SeckillGoodsCacheManager;
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
public class SeckillGoodsCacheManagerTest {

	@Resource
	SeckillGoodsCacheManager seckillGoodsCacheManager;

	@Test
	public void test() {
		seckillGoodsCacheManager.cache(121L);
		System.out.println(seckillGoodsCacheManager.cacheSeckillGoods(121L));
		System.out.println(seckillGoodsCacheManager.leftPush(121L, 121L));
		System.out.println(seckillGoodsCacheManager.seckillRemaining(121L, 121L));
	}

	@Test
	public void remove() {
		seckillGoodsCacheManager.cache(1L);
		for (int i = 0; i < 10; i++) {
			System.out.println(seckillGoodsCacheManager.deductSeckillGoods(1L, 1L));
		}
	}

}
