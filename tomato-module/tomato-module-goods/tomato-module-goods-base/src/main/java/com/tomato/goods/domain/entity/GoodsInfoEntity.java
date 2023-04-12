package com.tomato.goods.domain.entity;

import com.tomato.domain.core.entity.BaseEntity;
import lombok.Data;

/**
 * 商品信息实体
 *
 * @author lizhifu
 * @since 2023/3/21
 */
@Data
public class GoodsInfoEntity extends BaseEntity {
    /**
     * 商品名称
     */
    private String goodsName;

}
