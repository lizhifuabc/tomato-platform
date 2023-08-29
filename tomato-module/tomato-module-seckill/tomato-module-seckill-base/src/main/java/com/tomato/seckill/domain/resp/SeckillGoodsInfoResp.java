package com.tomato.seckill.domain.resp;

import lombok.Data;

/**
 * 商品基本信息
 *
 * @author lizhifu
 * @since 2023/3/22
 */
@Data
public class SeckillGoodsInfoResp {

	/**
	 * 商品id
	 */
	private Long goodsId;

	/**
	 * 商品名称
	 */
	private String goodsName;

}
