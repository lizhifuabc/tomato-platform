package com.tomato.order.domain.domain.entity;

import com.tomato.common.entity.BaseEntity;
import com.tomato.common.exception.BusinessException;
import com.tomato.order.domain.constants.OrderStatusEnum;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
@Builder
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
    /**
     * hmac 签名
     */
    private String hmac;

    /**
     * 订单初始化
     */
    public void init(){
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            this.machineIp = localHost.getHostAddress();
        } catch (UnknownHostException e) {
            this.clientIp = "0.0.0.0";
        }
        // 订单 5 分钟失效
        this.timeoutTime = LocalDateTime.now().plusMinutes(5);
    }

    /**
     * 完成订单
     * @param orderStatus 订单状态
     */
    public void complete(OrderStatusEnum orderStatus){
        // 判断订单状态只能是处理中或者初始化
        if(!OrderStatusEnum.INIT.getValue().equals(this.orderStatus) && !OrderStatusEnum.DEAL.getValue().equals(this.orderStatus)){
            throw new BusinessException("订单状态不正确");
        }
        this.orderStatus = orderStatus.getValue();
        this.completeTime = LocalDateTime.now();
        // version 由数据库维护，update时会自动加1
        // updateTime 由数据库维护,update时会自动更新
        // super.setVersion(getVersion() + 1);
    }

    /**
     * 处理订单
     */
    public void deal(){
        // 判断订单状态只能是初始化
        if(!OrderStatusEnum.INIT.getValue().equals(this.orderStatus)){
            throw new BusinessException("订单状态不正确");
        }
        this.orderStatus = OrderStatusEnum.DEAL.getValue();
        // version 由数据库维护，update时会自动加1
        // updateTime 由数据库维护,update时会自动更新
    }

    /**
     * 校验hmac
     * @param hmac hmac
     */
    public void checkHmac(String hmac){
        if (!this.hmac.equals(hmac)) {
            throw new BusinessException("hmac校验失败");
        }
    }
}