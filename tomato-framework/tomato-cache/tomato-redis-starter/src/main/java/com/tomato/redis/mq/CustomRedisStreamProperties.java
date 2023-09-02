package com.tomato.redis.mq;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * redis stream 配置
 *
 * @author lizhifu
 * @since 2023/9/1
 */
@Getter
@Setter
@ConfigurationProperties(
		prefix = "redis.custom.stream"
)
public class CustomRedisStreamProperties {
	/**
	 * 轮询超时
	 */
	private Duration pollTimeout = Duration.ofSeconds(2);
	/**
	 * 批量
	 */
	private Integer batchSize = 3;
}
