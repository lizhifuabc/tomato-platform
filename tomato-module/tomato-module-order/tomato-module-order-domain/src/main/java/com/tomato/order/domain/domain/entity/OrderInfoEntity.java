package com.tomato.order.domain.domain.entity;

import com.tomato.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单信息
 * 一个实体（Entity）是拥有ID的域对象，除了拥有数据之外，同时拥有行为。
 * Entity和数据库储存格式无关，在设计中要以该领域的通用严谨语言（Ubiquitous Language）为依据。
 * @author lizhifu
 * @since 2023/4/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderInfoEntity extends BaseEntity {
    /**
     * 收单服务器ip
     */
    private String machineIp;

    /**
     * 客户端ip
     */
    private String clientIp;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单金额
     */
    private BigDecimal requestAmount;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 入账状态
     */
    private String accountStatus;

    /**
     * 退款状态
     */
    private String refundStatus;

    /**
     * 通知状态
     */
    private String noticeStatus;

    /**
     * 完成时间
     */
    private LocalDateTime completeTime;

    /**
     * 订单失效时间
     */
    private LocalDateTime timeoutTime;

    /**
     * 订单类型：1 标准版 、2 专业版
     */
    private int orderType;

    /**
     * 支付方式：1 微信扫码 、2 支付宝扫码
     */
    private int payType;

    /**
     * 商户订单号
     */
    private String merchantOrderNo;

    /**
     * 商户编号
     */
    private String merchantNo;

    /**
     * 手续费
     */
    private BigDecimal merchantFee;

    /**
     * 商户手续费费率快照
     */
    private BigDecimal merchantRate;

    /**
     * 商户名称(冗余字段)
     */
    private String merchantName;

    /**
     * 页面通知地址
     */
    private String noticeWeb;

    /**
     * 系统通知地址
     */
    private String noticeSys;

    /**
     * 商户扩展参数
     */
    private String extParam;

    /**
     * 渠道返回码
     */
    private String channelCode;

    /**
     * 渠道返回信息
     */
    private String channelMsg;

    /**
     * 渠道返回订单号
     */
    private String channelOrderNo;
}
