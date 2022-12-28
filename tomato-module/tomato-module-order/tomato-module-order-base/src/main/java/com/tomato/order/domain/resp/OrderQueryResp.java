package com.tomato.order.domain.resp;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 订单查询
 *
 * @author lizhifu
 * @date 2022/12/12
 */
@Data
@Builder
public class OrderQueryResp {
    /**
     * 商户订单号
     */
    private String merchantOrderNo;
    /**
     * 商户编号
     */
    private String merchantNo;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 完成时间
     */
    private LocalDateTime completeTime;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 订单状态
     */
    private String orderStatus;
}
