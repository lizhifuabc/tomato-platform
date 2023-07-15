package com.tomato.seckill.goods.domain.entity;

import com.tomato.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 秒杀活动商品
 *
 * @author lizhifu
 * @since 2023/7/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SeckillGoods extends BaseEntity {
    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 活动id
     */
    private Long seckillActivityId;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 商品原价格
     */
    private BigDecimal originalPrice;

    /**
     * 商品秒杀价格
     */
    private BigDecimal seckillPrice;

    /**
     * 状态，0：已发布； 1：上线； 2：下线
     */
    private Integer status;

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

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品图片
     */
    private String imgUrl;
}
