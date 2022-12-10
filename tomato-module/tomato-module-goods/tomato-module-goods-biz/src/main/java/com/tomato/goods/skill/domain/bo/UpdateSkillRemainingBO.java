package com.tomato.goods.skill.domain.bo;

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
public class UpdateSkillRemainingBO {
    /**
     * 秒杀活动商品记录id
     */
    private Long userId;
    /**
     * 秒杀活动商品记录id
     */
    private Long skillGoodsId;
    /**
     * 版本号
     */
    private Integer version;
}
