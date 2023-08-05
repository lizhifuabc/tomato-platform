package com.tomato.order.application.req;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单查询结果返回
 *
 * @author lizhifu
 * @since 2023/4/7
 */
@Data
public class OrderQueryResultResp {
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 商户编号
     */
    private String merchantNo;

    /**
     * 商户订单号
     */
    private String merchantOrderNo;

    /**
     * 订单金额
     */
    private BigDecimal requestAmount;
}
