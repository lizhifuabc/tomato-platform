package com.tomato.rabbitmq.message;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;

import java.io.IOException;

/**
 * 消费者
 *
 * @author lizhifu
 * @since 2023/5/11
 */
@Slf4j
public abstract class AbstractConsumerService<T> implements ConsumerService {

	/**
	 * 消息
	 */
	private Message message;

	/**
	 * 通道
	 */
	private Channel channel;

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		this.message = message;
		this.channel = channel;
		String body = new String(message.getBody());
	}

	/**
	 * 拒绝消息
	 * @param multiple 当前 DeliveryTag 的消息是否确认所有 true 是， false 否
	 */
	protected void basicReject(Boolean multiple) throws IOException {
		this.channel.basicReject(this.message.getMessageProperties().getDeliveryTag(), multiple);
	}

	/**
	 * 是否自动确认
	 * @param multiple 当前 DeliveryTag 的消息是否确认所有 true 是， false 否
	 */
	protected void ack(Boolean multiple) throws IOException {
		this.channel.basicAck(this.message.getMessageProperties().getDeliveryTag(), multiple);
	}

	/**
	 * 拒绝消息
	 * @param multiple 当前 DeliveryTag 的消息是否确认所有 true 是， false 否
	 * @param requeue 当前 DeliveryTag 消息是否重回队列 true 是 false 否
	 */
	protected void nack(Boolean multiple, Boolean requeue) throws IOException {
		this.channel.basicNack(this.message.getMessageProperties().getDeliveryTag(), multiple, requeue);
	}

}
