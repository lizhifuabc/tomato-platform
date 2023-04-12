package com.tomato.order.client.dto;

import com.tomato.order.client.base.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 订单查询结果返回
 *
 * @author lizhifu
 * @since 2023/4/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderQueryResultDTO extends BaseDTO {
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
