package com.tomato.goods.seckill.controller;

import com.tomato.domain.resp.Resp;
import com.tomato.goods.domain.req.SeckillGoodsCreateListReq;
import com.tomato.goods.seckill.manager.SeckillGoodsManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀活动商品
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@RestController
public class SeckillGoodsController {
    private final SeckillGoodsManager seckillGoodsManager;

    public SeckillGoodsController(SeckillGoodsManager seckillGoodsManager) {
        this.seckillGoodsManager = seckillGoodsManager;
    }

    @PostMapping("/seckill/activity/goods/create")
    public Resp goodsCreate(@Validated @RequestBody SeckillGoodsCreateListReq listReq){
        seckillGoodsManager.goodsCreate(listReq);
        return Resp.buildSuccess();
    }
}
