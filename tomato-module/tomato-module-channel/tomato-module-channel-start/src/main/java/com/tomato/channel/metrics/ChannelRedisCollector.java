package com.tomato.channel.metrics;

import com.tomato.channel.service.ChannelRedisService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.Set;

/**
 * 从Redis收集指标并将其注册到MeterRegistry
 *
 * @author lizhifu
 * @since 2023/8/26
 */
// @Component
@Deprecated
// @EnableScheduling
@Slf4j
public class ChannelRedisCollector {

	private final MeterRegistry meterRegistry;

	private final ChannelRedisService channelRedisService;

	public ChannelRedisCollector(MeterRegistry meterRegistry, ChannelRedisService channelRedisService) {
		this.meterRegistry = meterRegistry;
		this.channelRedisService = channelRedisService;
	}

	/**
	 * 从Redis收集指标并将其注册到MeterRegistry 计划每10秒运行一次
	 */
	@Scheduled(fixedRate = 10000)
	public void collectMetrics() {
		log.info("从Redis收集指标并将其注册到MeterRegistry");
		Set<String> channelAlarm = channelRedisService.getChannelAlarm();
		for (String channel : channelAlarm) {
			collectMetrics(channel);
		}
	}

	private void collectMetrics(String channel) {
		// 从ZSet获取时间戳
		Set<String> timestamps = channelRedisService.getChannelRequest(channel);

		// 在时间戳上迭代并转换为 Micrometer timers
		for (String timestamp : timestamps) {
			long epochTime = Long.parseLong(timestamp);
			Timer timer = Timer.builder("channel_alarm_time").tag("channel", channel).register(meterRegistry);
			timer.record(Duration.ofSeconds(epochTime));

			Map<String, Long> channelResult = channelRedisService.getChannelResult(channel, epochTime);
			channelResult.forEach((key, value) -> {
				Counter counter = Counter.builder("channel_alarm_result")
					.tag("channel", channel)
					.tag("result", key)
					.register(meterRegistry);
				counter.increment(value);
			});
		}
	}

}
