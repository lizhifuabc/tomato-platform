package com.tomato.redis.mq;

import com.tomato.jackson.utils.JacksonUtils;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.connection.stream.StringRecord;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Collections;

/**
 * Redis Stream 消息生产者
 *
 * @author lizhifu
 * @since 2023/9/1
 */
public class RedisStreamMessageProducer {
	private final StringRedisTemplate stringRedisTemplate;

	public RedisStreamMessageProducer(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	/**
	 * 发送 Stream 消息
	 * @param queue 消息队列
	 * @param jsonSerializableObject 消息对象
	 * @param <T> 消息对象类型
	 */
	public <T> void sendStreamMessage(String queue, T jsonSerializableObject) {
		String data = JacksonUtils.toJson(jsonSerializableObject);
		// 创建消息记录, 指定stream
		StringRecord stringRecord = StreamRecords.string(Collections.singletonMap("data", data)).withStreamKey(queue);
		// 将消息添加至消息队列中
		this.stringRedisTemplate.opsForStream().add(stringRecord);
	}
}
