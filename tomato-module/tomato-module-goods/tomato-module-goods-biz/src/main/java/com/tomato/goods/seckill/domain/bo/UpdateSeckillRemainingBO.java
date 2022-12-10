package com.tomato.goods.seckill.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 更新抢购数量
 *
 * @author lizhifu
 * @date 2022/12/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateSeckillRemainingBO {
    /**
     * 秒杀活动商品记录id
     */
    private Long userId;
    /**
     * 秒杀活动商品记录id
     */
    private Long seckillGoodsId;
    /**
     * 版本号
     */
    private Integer version;
}
