package com.tomato.order.infrastructure.dataobject;

import com.tomato.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单基础信息
 *
 * @author lizhifu
 * @since 2023/8/1
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderInfoBaseDO extends BaseEntity {

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
	 * 完成时间
	 */
	private LocalDateTime completeTime;

	/**
	 * 订单失效时间
	 */
	private LocalDateTime timeoutTime;

}
