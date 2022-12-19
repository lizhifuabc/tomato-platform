package com.tomato.goods.domain.entity;

import com.tomato.domain.entity.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 秒杀活动记录
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Data
public class SeckillActivityEntity extends BaseEntity {
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 活动描述
     */
    private String activityDesc;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
}
