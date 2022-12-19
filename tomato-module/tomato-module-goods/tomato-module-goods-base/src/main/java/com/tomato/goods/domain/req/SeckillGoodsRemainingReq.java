package com.tomato.goods.domain.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 剩余库存查询
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Data
public class SeckillGoodsRemainingReq {
    /**
     * 商品id
     */
    @NotNull(message = "商品id不能为空")
    private Long goodsId;
    /**
     * 活动id
     */
    @NotNull(message = "活动id不能为空")
    private Long seckillActivityId;
}
