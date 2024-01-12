package com.tomato.seckill.goods.interfaces.controller;

import com.tomato.common.domain.resp.Resp;
import com.tomato.seckill.goods.application.req.SeckillGoodsCreateReq;
import com.tomato.seckill.goods.application.service.SeckillGoodsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品接口
 *
 * @author lizhifu
 * @since 2023/7/14
 */
@RestController
@RequestMapping(value = "/goods")
public class SeckillGoodsController {

	@Autowired
	private SeckillGoodsService seckillGoodsService;

	/**
	 * 保存秒杀商品
	 */
	@RequestMapping(value = "/saveSeckillGoods", method = { RequestMethod.POST })
	public Resp<Void> saveSeckillActivityDTO(@Valid @RequestBody SeckillGoodsCreateReq seckillGoodsCreateReq) {
		seckillGoodsService.saveSeckillGoods(seckillGoodsCreateReq);
		return Resp.buildSuccess();
	}

}
