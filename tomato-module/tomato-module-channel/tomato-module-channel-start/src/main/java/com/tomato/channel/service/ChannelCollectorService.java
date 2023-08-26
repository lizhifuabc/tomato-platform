package com.tomato.channel.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * <p>Description: 收集指标并将其注册到MeterRegistry</p>
 *
 * @author lizhifu
 * @since 2023/8/26
 */
@Service
public class ChannelCollectorService {
    private final MeterRegistry meterRegistry;

    public ChannelCollectorService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    /**
     * 收集指标并将其注册到MeterRegistry
     * @param channel 渠道
     */
    public void collectRequestMetrics(String channel) {
        Timer timer = Timer.builder("channel_alarm_time")
                .tag("channel", channel)
                .register(meterRegistry);
        // 记录时间
        timer.record(()-> LocalDateTime.now());
    }
    /**
     * 收集指标并将其注册到MeterRegistry
     * @param channel 渠道
     */
    public void collectResultMetrics(String channel,String type) {
        Counter counter = Counter.builder("channel_alarm_result")
                .tag("channel", channel)
                .tag("result", type)
                .register(meterRegistry);
        counter.increment(1);
    }
}
