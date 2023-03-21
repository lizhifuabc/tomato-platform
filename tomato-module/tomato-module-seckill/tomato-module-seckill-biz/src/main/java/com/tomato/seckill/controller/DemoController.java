package com.tomato.seckill.controller;

import com.tomato.domain.resp.SingleResp;
import com.tomato.goods.domain.resp.GoodsInfoResp;
import com.tomato.goods.feign.RemoteGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * demo
 *
 * @author lizhifu
 * @since 2023/3/21
 */
@RestController
@Slf4j
public class DemoController {
    private final RemoteGoodsService remoteGoodsService;

    public DemoController(RemoteGoodsService remoteGoodsService) {
        this.remoteGoodsService = remoteGoodsService;
    }

    @GetMapping("/demo")
    public String demo(){
        SingleResp<GoodsInfoResp> goodsInfoRespSingleResp = remoteGoodsService.queryGoodsInfo(1L);
        log.info("goodsInfoRespSingleResp:{}",goodsInfoRespSingleResp);
        return goodsInfoRespSingleResp.getData().getGoodsName();
    }
}
