package com.tomato.pay.domain.constants;

/**
 * mq常量
 *
 * @author lizhifu
 * @since 2023/4/12
 */
public interface PayMqConstant {

	/**
	 * 支付结果通知
	 */
	String PAY_RESULT_EXCHANGE = "pay.result.exchange";

	/**
	 * 支付结果通知
	 */
	String PAY_RESULT_ORDER_QUEUE = "pay.result.order.queue";

	/**
	 * 支付结果通知
	 */
	String PAY_RESULT_NOTICE_QUEUE = "pay.result.notice.queue";

}
