package com.tomato.goods.seckill.controller;

import com.tomato.domain.resp.SingleResp;
import com.tomato.goods.constant.SeckillResultEnum;
import com.tomato.goods.domain.req.SeckillReq;
import com.tomato.goods.domain.resp.SeckillResp;
import com.tomato.goods.seckill.manager.SeckillGoodsRedisManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀 Redis 队列实现
 *
 * @author lizhifu
 * @date 2022/12/10
 */
@RestController
public class SeckillQueueController {
    private final SeckillGoodsRedisManager seckillGoodsRedisManager;

    public SeckillQueueController(SeckillGoodsRedisManager seckillGoodsRedisManager) {
        this.seckillGoodsRedisManager = seckillGoodsRedisManager;
    }

    /**
     * 用户秒杀
     * @return 用户秒杀
     */
    @PostMapping("/seckill/queue/user/seckill")
    public SingleResp<SeckillResp> seckill(@Validated @RequestBody SeckillReq seckillReq){
        SeckillResp seckillResp = new SeckillResp();
        seckillResp.setSeckillResult(SeckillResultEnum.SUCCESS.getValue());
        return SingleResp.of(seckillResp);
    }
}
