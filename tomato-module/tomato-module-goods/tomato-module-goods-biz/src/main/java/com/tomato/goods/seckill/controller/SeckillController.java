package com.tomato.goods.seckill.controller;

import com.tomato.domain.resp.SingleResp;
import com.tomato.goods.constant.SeckillResultEnum;
import com.tomato.goods.resp.SeckillResultResp;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀
 *
 * @author lizhifu
 * @date 2022/12/10
 */
@RestController
public class SeckillController {
    /**
     * 秒杀结果查询
     * @return
     */
    @RequestMapping("/seckill/result")
    public SingleResp<SeckillResultResp> seckillResult(){
        SeckillResultResp seckillResultResp = new SeckillResultResp();
        seckillResultResp.setSeckillResult(SeckillResultEnum.SUCCESS.getValue());
        return SingleResp.of(seckillResultResp);
    }
}
