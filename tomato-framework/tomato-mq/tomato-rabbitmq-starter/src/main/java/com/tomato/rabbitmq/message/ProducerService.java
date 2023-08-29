package com.tomato.rabbitmq.message;

/**
 * 生产者
 *
 * @author lizhifu
 * @since 2023/5/11
 */
public interface ProducerService {

	/**
	 * 发送消息
	 * @param message 消息
	 */
	void send(Object message);

}
