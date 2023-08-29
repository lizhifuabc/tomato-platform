package com.tomato.seckill.goods.application.builder;

import com.tomato.seckill.goods.application.req.SeckillGoodsCreateReq;
import com.tomato.seckill.goods.domain.entity.SeckillGoods;
import org.springframework.beans.BeanUtils;

/**
 * 秒杀商品转化类
 *
 * @author lizhifu
 * @since 2023/7/14
 */
public class SeckillGoodsBuilder {

	public static SeckillGoods toSeckillGoods(SeckillGoodsCreateReq seckillGoodsCreateReq) {
		if (seckillGoodsCreateReq == null) {
			return null;
		}
		SeckillGoods seckillGoods = new SeckillGoods();
		BeanUtils.copyProperties(seckillGoodsCreateReq, seckillGoods);
		return seckillGoods;
	}

}
