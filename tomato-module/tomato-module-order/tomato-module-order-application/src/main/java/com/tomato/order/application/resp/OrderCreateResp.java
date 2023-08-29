package com.tomato.order.application.resp;

import lombok.Data;

/**
 * 订单创建返回
 *
 * @author lizhifu
 * @since 2023/8/1
 */
@Data
public class OrderCreateResp {

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 商户订单号
	 */
	private String merchantOrderNo;

	/**
	 * 商户编号
	 */
	private String merchantNo;

	/**
	 * 商户扩展参数
	 */
	private String extParam;

}
