package com.tomato.seckill.service.cache;

import com.tomato.seckill.domain.req.SeckillUserReq;
import com.tomato.seckill.manager.SeckillGoodsCacheManager;
import com.tomato.seckill.service.SeckillCheckService;
import com.tomato.seckill.service.SeckillGoodsService;
import com.tomato.seckill.service.SeckillRedisCheckService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 秒杀主流程
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@SpringBootTest
public class SeckillMainTest {

	@Resource
	SeckillRedisCheckService seckillRedisCheckService;

	@Resource
	SeckillCheckService seckillCheckService;

	@Resource
	SeckillGoodsCacheManager seckillGoodsCacheManager;

	@Resource
	SeckillGoodsService seckillGoodsService;

	@Test
	public void seckill() {
		// 验证码限流，建议使用滑块验证码，安全性较高

		// 测试参数构建
		String ip = "127.0.0.1";
		SeckillUserReq seckillUserReq = new SeckillUserReq();
		seckillUserReq.setSeckillGoodsId(2L);
		seckillUserReq.setSeckillActivityId(1L);
		seckillUserReq.setUserId(20001L);
		seckillUserReq.setGoodsId(2L);
		// 接口请求次数限流 TODO
		// 同一ip限流 && 同一用户限流
		seckillRedisCheckService.checkUserIp(ip, seckillUserReq.getUserId());
		// 活动校验
		seckillCheckService.checkSeckillActivity(seckillUserReq.getSeckillActivityId());
		// 用户抢购次数校验，对于用户来讲，此处不存在大并发，通过同一用户限流可以保证用户抢购次数问题
		seckillCheckService.checkSeckillUser(seckillUserReq.getUserId(), seckillUserReq.getSeckillGoodsId());
		// 用户存在抢购未支付订单，不允许进行抢购 TODO

		// 数据库 校验商品库存
		seckillCheckService.checkSeckillGoods(seckillUserReq.getSeckillGoodsId());
		// redis 校验商品库存
		seckillRedisCheckService.checkSeckillGoods(seckillUserReq.getSeckillGoodsId(),
				seckillUserReq.getSeckillActivityId());

		// 执行抢购
		// redis 扣减库存
		seckillGoodsCacheManager.deductSeckillGoods(seckillUserReq.getSeckillGoodsId(),
				seckillUserReq.getSeckillActivityId());
		// 数据库扣减库存 && 用户抢购记录
		seckillGoodsService.deductSeckillGoods(seckillUserReq.getSeckillGoodsId(), seckillUserReq.getUserId());

		// 开始进行支付下单逻辑
	}

}
