package com.tomato.goods.domain.entity;

import com.tomato.common.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品信息实体
 *
 * @author lizhifu
 * @since 2023/3/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsInfoEntity extends BaseEntity {

	/**
	 * 商品名称
	 */
	private String goodsName;

}
