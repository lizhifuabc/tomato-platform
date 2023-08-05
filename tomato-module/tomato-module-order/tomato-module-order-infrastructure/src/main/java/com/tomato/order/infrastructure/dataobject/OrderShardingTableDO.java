package com.tomato.order.infrastructure.dataobject;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 订单分表
 *
 * @author lizhifu
 * @since 2023/8/5
 */
@Data
public class OrderShardingTableDO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
}
