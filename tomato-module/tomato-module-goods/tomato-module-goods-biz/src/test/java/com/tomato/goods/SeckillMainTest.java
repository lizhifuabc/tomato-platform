package com.tomato.goods;

import com.tomato.goods.domain.entity.SeckillGoodsEntity;
import com.tomato.goods.domain.req.SeckillReq;
import com.tomato.goods.seckill.dao.SeckillGoodsDao;
import com.tomato.goods.seckill.manager.SeckillGoodsRedisManager;
import com.tomato.goods.seckill.manager.SeckillUserManager;
import com.tomato.goods.seckill.service.SeckillCheckService;
import com.tomato.goods.seckill.service.SeckillGoodsService;
import com.tomato.goods.seckill.service.SeckillRedisCheckService;
import com.tomato.redis.domain.req.RedisConcurrentRequestCountLimiterReq;
import com.tomato.redis.ratelimit.RedisConcurrentRequestCountLimiter;
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
    SeckillGoodsRedisManager seckillGoodsRedisManager;
    @Resource
    SeckillGoodsService seckillGoodsService;
    @Test
    public void seckill(){
        // 验证码限流，建议使用滑块验证码，安全性较高

        // 测试参数构建
        String ip = "127.0.0.1";
        SeckillReq seckillReq = new SeckillReq();
        seckillReq.setSeckillGoodsId(1L);
        seckillReq.setSeckillActivityId(1L);
        seckillReq.setUserId(20003L);
        seckillReq.setGoodsId(1L);
        // 接口请求次数限流 TODO
        // 同一ip限流 && 同一用户限流
        seckillRedisCheckService.checkUserIp(ip,seckillReq.getUserId());
        // 活动校验
        seckillCheckService.checkSeckillActivity(seckillReq.getSeckillActivityId());
        // 用户抢购次数校验，对于用户来讲，此处不存在大并发，通过同一用户限流可以保证用户抢购次数问题
        seckillCheckService.checkSeckillUser(seckillReq.getUserId(),seckillReq.getSeckillGoodsId());
        // 用户存在抢购未支付订单，不允许进行抢购 TODO

        // 数据库 校验商品库存
        seckillCheckService.checkSeckillGoods(seckillReq.getSeckillGoodsId());
        // redis 校验商品库存
        seckillRedisCheckService.checkSeckillGoods(seckillReq.getSeckillGoodsId(),seckillReq.getSeckillActivityId());

        // 执行抢购
        // redis 扣减库存
        String rightPop = seckillGoodsRedisManager.rightPop(seckillReq.getSeckillGoodsId(), seckillReq.getSeckillActivityId());
        System.out.println("redis 扣减库存:"+rightPop);
        // 数据库扣减库存 && 用户抢购记录
        seckillGoodsService.deductSeckillGoods(seckillReq.getSeckillGoodsId(),seckillReq.getUserId());

        // 开始进行支付下单逻辑
    }
}
