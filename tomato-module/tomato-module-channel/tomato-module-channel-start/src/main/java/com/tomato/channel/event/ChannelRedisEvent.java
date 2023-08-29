package com.tomato.channel.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * <p>
 * 渠道 redis 事件
 * </p>
 *
 * @author lizhifu
 * @since 2023/8/26
 */
@Getter
public class ChannelRedisEvent extends ApplicationEvent {

	private final ChannelRedisEventData channelRedisEventData;

	public ChannelRedisEvent(ChannelRedisEventData channelRedisEventData) {
		super(channelRedisEventData);
		this.channelRedisEventData = channelRedisEventData;
	}

}
