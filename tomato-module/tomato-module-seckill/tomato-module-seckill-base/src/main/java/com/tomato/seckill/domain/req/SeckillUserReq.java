package com.tomato.seckill.domain.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 秒杀
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Data
// Schema 注解设置这个类的描述
//@Schema(description = "秒杀 请求参数")
public class SeckillUserReq {
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
    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    private Long userId;
    /**
     * 秒杀活动商品记录id
     */
    @NotNull(message = "秒杀活动商品记录id不能为空")
    private Long seckillGoodsId;
}
