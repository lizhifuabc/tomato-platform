package com.tomato.channel.domain;

import com.tomato.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 渠道
 *
 * @author lizhifu
 * @since 2023/8/31
 */
@Getter
@Setter
@ToString
@Entity(name = "t_merchant_router_rule")
public class PayChannel extends BaseEntity {
	/**
	 * 渠道编号
	 */
	private String channelNo;

	/**
	 * 渠道名称
	 */
	private String channelName;

	/**
	 * 渠道成本百分比，例如0.02表示2%
	 */
	private BigDecimal channelRate;

	/**
	 * 渠道描述
	 */
	private String channelDescription;

	/**
	 * 状态，active表示可用，inactive表示不可用
	 */
	private String status;

	/**
	 * 支付方式
	 */
	private String payType;

	/**
	 * 渠道交易限额
	 */
	private BigDecimal transactionLimit;

	/**
	 * 渠道平均交易处理时间
	 */
	private BigDecimal processingTime;

	/**
	 * 渠道单日限额
	 */
	private BigDecimal dailyLimit;

	/**
	 * 渠道单日笔数限额
	 */
	private Integer dailyTransactionsLimit;

	/**
	 * 渠道单笔金额限制
	 */
	private BigDecimal singleTransactionLimit;

	/**
	 * 渠道单日资金累计限额
	 */
	private BigDecimal dailyAccumulatedLimit;

	/**
	 * 渠道单日可使用时间
	 */
	private String dailyUsageTime;
}
