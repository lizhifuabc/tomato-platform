package com.tomato.seckill.controller;

import com.tomato.common.resp.Resp;
import com.tomato.seckill.domain.req.SeckillGoodsInfoQueryReq;
import com.tomato.seckill.domain.resp.SeckillGoodsInfoResp;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 秒杀商品查询
 *
 * @author lizhifu
 * @since 2023/3/22
 */
@RestController
public class SeckillGoodsQueryController {

	/**
	 * 根据活动id查询秒杀商品基本信息
	 * @param queryReq 查询请求
	 */
	@PostMapping("/seckill/goods/info/list")
	public Resp<List<SeckillGoodsInfoResp>> querySeckillGoodsInfoListByActivityId(
			@RequestBody @Valid SeckillGoodsInfoQueryReq queryReq) {

		return Resp.of(null);
	}

}
