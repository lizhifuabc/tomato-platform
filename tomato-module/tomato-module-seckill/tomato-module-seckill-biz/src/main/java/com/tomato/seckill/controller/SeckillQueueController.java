package com.tomato.seckill.controller;

import com.tomato.domain.resp.SingleResp;
import com.tomato.seckill.constant.SeckillResultEnum;
import com.tomato.seckill.domain.req.SeckillReq;
import com.tomato.seckill.domain.resp.SeckillResp;
import com.tomato.seckill.manager.SeckillGoodsCacheManager;
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
    private final SeckillGoodsCacheManager seckillGoodsCacheManager;

    public SeckillQueueController(SeckillGoodsCacheManager seckillGoodsCacheManager) {
        this.seckillGoodsCacheManager = seckillGoodsCacheManager;
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
