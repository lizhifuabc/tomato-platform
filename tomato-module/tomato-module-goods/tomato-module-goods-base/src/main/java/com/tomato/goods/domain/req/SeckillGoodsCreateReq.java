package com.tomato.goods.domain.req;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 秒杀活动商品
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Data
public class SeckillGoodsCreateReq {
    /**
     * 商品id
     */
    @NotNull(message = "商品id不能为空")
    private Long goodsId;
    /**
     * 秒杀价格
     */
    @NotNull(message = "秒杀价格不能为空")
    @DecimalMin(value = "0.00", message = "秒杀价格格式不正确")
    private BigDecimal seckillPrice;
    /**
     * 秒杀总量
     */
    @NotNull(message = "秒杀总量不能为空")
    @Min(0)
    private Integer seckillCount;
    /**
     * 每人限购数量
     */
    @NotNull(message = "每人限购数量不能为空")
    @Min(0)
    private Integer seckillLimit;
    /**
     * 排序
     */
    @Min(0)
    private Integer seckillSort;
}
