package com.tomato.order.domain.entity;

import com.tomato.domain.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体
 * 类中字段要与数据库字段保持一致，不能缺失或者多余
 * 类中的每个字段添加注释，并与数据库注释保持一致
 * 不允许有组合
 *
 * @author lizhifu
 * @date 2022/12/2
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderEntity extends BaseEntity {
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
