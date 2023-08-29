package com.tomato.channel.event;

import lombok.*;

/**
 * <p>
 * 渠道 redis 事件
 * </p>
 *
 * @author lizhifu
 * @since 2023/8/26
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class ChannelRedisEventData {

	/**
	 * 支付类型
	 */
	String payType;

	/**
	 * 渠道号
	 */
	String channelNo;

	/**
	 * 结果类型
	 */
	String resultType;

}
