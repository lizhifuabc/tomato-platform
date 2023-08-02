package com.tomato.order.infrastructure.dataobject;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 延迟订单
 *
 * @author lizhifu
 * @since 2022/12/26
 */
@Data
@Builder
public class OrderDelayDO {
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 订单失效时间
     */
    private LocalDateTime timeoutTime;
}
