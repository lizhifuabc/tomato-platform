package com.tomato.redis.mq;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.util.ErrorHandler;

import java.util.Map;

/**
 * TODO
 *
 * @author lizhifu
 * @since 2023/9/1
 */
public class CustomStreamMessageListenerContainer {
	private final RedisConnectionFactory redisConnectionFactory;

	private final ErrorHandler errorHandler;
	private final CustomRedisStreamProperties customRedisStreamProperties;


	public CustomStreamMessageListenerContainer(RedisConnectionFactory redisConnectionFactory, ErrorHandler errorHandler, CustomRedisStreamProperties customRedisStreamProperties) {
		this.redisConnectionFactory = redisConnectionFactory;
		this.errorHandler = errorHandler;
		this.customRedisStreamProperties = customRedisStreamProperties;
	}

	public void exe(){
		StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> containerOptions =
				StreamMessageListenerContainer.StreamMessageListenerContainerOptions
						.builder()
						// 一次最多获取多少条消息
						.batchSize(customRedisStreamProperties.getBatchSize())
						// 运行 Stream 的 poll task
						.executor(AsyncExecutor.asyncExecutor())
						// Stream 中没有消息时，阻塞多长时间，需要比 `spring.redis.timeout` 的时间小
						.pollTimeout(customRedisStreamProperties.getPollTimeout())
						// 获取消息的过程或获取到消息给具体的消息者处理的过程中，发生了异常的处理
						.errorHandler(errorHandler)
						.build();
		StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer =
				StreamMessageListenerContainer.create(redisConnectionFactory, containerOptions);


	}
}
