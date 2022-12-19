package com.tomato.goods.domain.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 秒杀活动商品
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Data
public class SeckillGoodsCreateListReq {
    /**
     * 活动id
     */
    @NotNull(message = "活动id不能为空")
    private Long seckillActivityId;
    @Valid
    @Size(min = 1, max = 10)
    @NotNull
    private List<SeckillGoodsCreateReq> goodsList;
}
