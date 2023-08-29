package com.tomato.channel.event;

import com.tomato.channel.service.ChannelCollectorService;
import com.tomato.channel.service.ChannelRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 渠道 redis 事件监听
 * </p>
 *
 * @author lizhifu
 * @since 2023/8/26
 */
@Order(0)
@Component
@RequiredArgsConstructor
@Slf4j
@Async
public class ChannelRedisEventListener implements ApplicationListener<ChannelRedisEvent> {

	private final ChannelRedisService channelRedisService;

	private final ChannelCollectorService channelCollectorService;

	@Override
	public void onApplicationEvent(ChannelRedisEvent event) {
		ChannelRedisEventData channelRedisEventData = event.getChannelRedisEventData();
		log.info("ChannelRedisEventListener 渠道 redis 事件监听:{}", channelRedisEventData);

		channelRedisService.addChannelAlarm(channelRedisEventData.getPayType(), channelRedisEventData.getChannelNo());
		long request = channelRedisService.addChannelRequest(channelRedisEventData.getPayType(),
				channelRedisEventData.getChannelNo());
		channelRedisService.addChannelResult(channelRedisEventData.getPayType(), channelRedisEventData.getChannelNo(),
				channelRedisEventData.getResultType(), request);

		channelCollectorService.collectRequestMetrics(channelRedisEventData.getChannelNo());
		channelCollectorService.collectResultMetrics(channelRedisEventData.getChannelNo(),
				channelRedisEventData.getResultType());
	}

}