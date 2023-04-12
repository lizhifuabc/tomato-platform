package com.tomato.seckill.domain.entity;

import com.tomato.domain.core.entity.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 秒杀活动记录
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Data
public class SeckillInfoEntity extends BaseEntity {
    /**
     * 活动名称
     */
    private String name;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 是否关闭,0-否, 1-是
     */
    private Boolean disabledFlag;
}
