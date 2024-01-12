package com.tomato.seckill.domain.req;

import com.tomato.common.domain.req.PageQueryReq;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 商品基本信息查询请求
 *
 * @author lizhifu
 * @since 2023/3/22
 */
@Data
public class SeckillGoodsInfoQueryReq extends PageQueryReq {

	/**
	 * 活动id
	 */
	@NotNull(message = "活动id不能为空")
	private Long seckillActivityId;

}
