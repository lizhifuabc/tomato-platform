package com.tomato.goods.domain.entity;

import com.tomato.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 秒杀活动商品
 *
 * @author lizhifu
 * @date 2022/12/10
 */
@Data
public class SeckillGoodsEntity extends BaseEntity {
    /**
     * 商品id
     */
    private Long goodsId;
    /**
     * 活动id
     */
    private Long seckillActivityId;
    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;
    /**
     * 秒杀总量
     */
    private Integer seckillCount;
    /**
     * 秒杀剩余量
     */
    private Integer seckillRemaining;
    /**
     * 每人限购数量
     */
    private Integer seckillLimit;
    /**
     * 排序
     */
    private Integer seckillSort;
}
