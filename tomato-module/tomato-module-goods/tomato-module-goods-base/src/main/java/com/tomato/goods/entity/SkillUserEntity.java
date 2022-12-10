package com.tomato.goods.entity;

import com.tomato.domain.entity.BaseEntity;
import lombok.Data;

/**
 * 用户参与活动记录
 *
 * @author lizhifu
 * @date 2022/12/10
 */
@Data
public class SkillUserEntity extends BaseEntity {
    /**
     * 商品表主键
     */
    private Long goodsId;
    /**
     * 秒杀活动商品记录id
     */
    private Long skillGoodsId;
    /**
     * 秒杀活动商品记录id
     */
    private Long userId;
    /**
     * 秒杀总量
     */
    private Integer skillCount;
    /**
     * 秒杀剩余量
     */
    private Integer skillRemaining;
}
