package com.tomato.pay.domain.event;

import com.tomato.domain.core.DomainEvent;

/**
 * 支付结果事件
 *
 * @author lizhifu
 * @since 2023/4/12
 */
public class PayResultEvent implements DomainEvent {
    /**
     * 支付状态
     */
    private String payStatus;
    /**
     * 支付方式
     */
    private String payType;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 商户订单号
     */
    private String merchantOrderNo;
    /**
     * 支付号
     */
    private String payNo;
    /**
     * 商户编号
     */
    private String merchantNo;
}
