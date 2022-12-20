package com.tomato.order.domain.entity;

import com.tomato.domain.entity.BaseEntity;
import lombok.Data;

/**
 * 订单实体
 * 类中字段要与数据库字段保持一致，不能缺失或者多余
 * 类中的每个字段添加注释，并与数据库注释保持一致
 * 不允许有组合
 *
 * @author lizhifu
 * @date 2022/12/2
 */
@Data
public class OrderEntity extends BaseEntity {
    private String noticeWeb;
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
     * 支付方式：1 微信扫码 、2 支付宝扫码
     */
    private Integer payType;
}
