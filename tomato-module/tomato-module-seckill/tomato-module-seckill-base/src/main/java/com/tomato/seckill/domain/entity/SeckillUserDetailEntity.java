package com.tomato.seckill.domain.entity;

import com.tomato.domain.entity.BaseEntity;
import lombok.Data;

/**
 * 用户参与活动明细
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Data
public class SeckillUserDetailEntity extends BaseEntity {
    /**
     * 用户参与活动记录id {@link SeckillUserEntity}
     */
    private Long seckillUserId;
}
