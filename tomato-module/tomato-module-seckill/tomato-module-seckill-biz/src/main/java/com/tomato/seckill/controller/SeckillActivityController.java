package com.tomato.seckill.controller;

import com.tomato.common.resp.Resp;
import com.tomato.seckill.domain.req.SeckillActivityCreateReq;
import com.tomato.seckill.service.SeckillActivityService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀活动记录
 *
 * @author lizhifu
 * @since 2022/12/18
 */
@RestController
public class SeckillActivityController {

	private final SeckillActivityService seckillActivityService;

	public SeckillActivityController(SeckillActivityService seckillActivityService) {
		this.seckillActivityService = seckillActivityService;
	}

	/**
	 * 创建秒杀活动 <a href="https://mp.weixin.qq.com/s/268i0R3yxA8GWMVrCs3M1A">秒杀</a>
	 * <p>
	 * 1. 插入 mysql 的秒杀活动表
	 * </p>
	 * <p>
	 * 2. 注册定时任务到分布式调度中心，用于预热商品数据
	 * </p>
	 * <p>
	 * 3. 商品数据预热到 redis 的 List 队列，通过 lpus 入队列，一个元素表示一个商品
	 * </p>
	 * <p>
	 * 4. 点击抢购，首先流量限流，需要判断有没库存 ，通过llen判断有没队列长度是否为0，不是的话，就通过rpop命令出队。
	 * 表中（如果异常情况下，redis挂了，可以通过秒杀活动商品记录和抢购记录恢复redis商品队列数据）
	 * </p>
	 * <p>
	 * 5. 步骤4的同时，插入抢购成功记录到mysql和要发一个延时消息到rocketmq，如果用户下单支付，5分钟之后，恢复商品数据到redis list队列里面
	 * </p>
	 * <p>
	 * 6. 抢到商品之后，进入到立即下单页面，点击立即下单，就进入结算页，冻结库存，支付，减库存
	 * </p>
	 * @return 创建秒杀活动结果
	 */
	@PostMapping("/seckill/activity/create")
	public Resp create(@Validated @RequestBody SeckillActivityCreateReq seckillActivityCreateReq) {
		seckillActivityService.create(seckillActivityCreateReq);
		return Resp.buildSuccess();
	}

}
