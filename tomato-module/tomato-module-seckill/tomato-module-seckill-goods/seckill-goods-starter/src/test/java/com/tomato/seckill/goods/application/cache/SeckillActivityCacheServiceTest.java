package com.tomato.seckill.goods.application.cache;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 秒杀活动缓存接口
 *
 * @author lizhifu
 * @since 2023/7/15
 */
@SpringBootTest
public class SeckillActivityCacheServiceTest {

	@Resource
	private SeckillActivityCacheService seckillActivityCacheService;

	@Test
	public void test() {
		Long activityId = 3L;
		System.out.println(seckillActivityCacheService.getLocalOrDistributedCache(activityId, 1L));
		System.out.println(seckillActivityCacheService.tryUpdate(activityId, true));
		System.out.println(seckillActivityCacheService.getDistributedCache(activityId));
	}

}
