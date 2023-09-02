package com.tomato.redis.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;

/**
 * 默认流监听器，接收到的消息
 *
 * @author lizhifu
 * @since 2023/9/1
 */
@Slf4j
public class DefaultGroupStreamListener implements StreamListener<String, MapRecord<String, String, String>> {
	@Override
	public void onMessage(MapRecord<String, String, String> message) {

	}
}
