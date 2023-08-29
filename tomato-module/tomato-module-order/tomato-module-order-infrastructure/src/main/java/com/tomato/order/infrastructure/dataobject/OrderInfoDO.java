package com.tomato.order.infrastructure.dataobject;

import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 订单
 *
 * @author lizhifu
 * @since 2023/8/1
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "t_order_info")
public class OrderInfoDO extends OrderInfoBaseDO {

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
	 * 收单服务器ip
	 */
	private String machineIp;

	/**
	 * 客户端ip
	 */
	private String clientIp;

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
	 * 分账费率快照
	 */
	private BigDecimal merchantSplitRate;

	/**
	 * 分账手续费
	 */
	private BigDecimal merchantSplitFee;

}
