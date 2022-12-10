package com.tomato.goods.entity;

import com.tomato.domain.entity.BaseEntity;
import lombok.Data;

/**
 * 秒杀活动商品
 *
 * @author lizhifu
 * @date 2022/12/10
 */
@Data
public class SeckillGoodsEntity extends BaseEntity {
    /**
     * 商品表主键
     */
    private Long goodsId;
}
