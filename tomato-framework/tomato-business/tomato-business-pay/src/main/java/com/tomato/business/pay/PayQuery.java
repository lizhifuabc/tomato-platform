package com.tomato.business.pay;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单查询
 *
 * @author lizhifu
 * @since 2024/3/13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PayQuery extends AbstractPayCommon {

	/**
	 * 支付订单号，发送支付通道的订单号
	 */
	private String payNo;

	/**
	 * 商户订单号
	 */
	private String merchantOrderNo;
}
