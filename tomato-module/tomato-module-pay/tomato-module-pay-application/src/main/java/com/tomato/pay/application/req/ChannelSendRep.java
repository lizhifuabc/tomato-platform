package com.tomato.pay.application.req;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 发送第三方通道请求返回
 *
 * @author lizhifu
 * @date 2022/6/24
 */
@Data
public class ChannelSendRep {

	/**
	 * 通道返回支付信息
	 */
	private String sendUrl;

	/**
	 * 支付信息/错误信息
	 */
	private String remarksInfo;

	/**
	 * 通道成本费率
	 */
	private BigDecimal channelRate;

	/**
	 * 通道标识
	 */
	private String channelFlag;

}
