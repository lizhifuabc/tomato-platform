package com.tomato.goods;

import com.tomato.domain.exception.BusinessException;
import com.tomato.goods.domain.req.SeckillReq;
import com.tomato.goods.seckill.manager.SeckillGoodsRedisManager;
import com.tomato.goods.seckill.service.SeckillCheckService;
import com.tomato.goods.seckill.service.SeckillRedisCheckService;
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
    @Test
    public void seckill(){
        // 测试参数构建
        SeckillReq seckillReq = new SeckillReq();
        seckillReq.setSeckillGoodsId(1L);
        seckillReq.setSeckillActivityId(1L);
        seckillReq.setUserId(20000L);
        seckillReq.setGoodsId(1L);
        // 同一ip限流
        // 同一用户限流
        // 接口请求次数限流
        // 验证码限流，建议使用滑块验证码，安全性较高


        // 活动校验
        seckillCheckService.checkSeckillActivity(seckillReq.getSeckillActivityId());
        // 用户抢购次数校验，对于用户来讲，此处不存在并发，通过同一用户限流可以保证用户抢购次数问题
        seckillCheckService.checkSeckillUser(seckillReq.getUserId(),seckillReq.getSeckillGoodsId());
        // 数据库 校验商品库存
        seckillCheckService.checkSeckillGoods(seckillReq.getSeckillGoodsId());
        // redis 校验商品库存
        seckillRedisCheckService.checkSeckillGoods(seckillReq.getSeckillGoodsId(),seckillReq.getSeckillActivityId());
    }
}
