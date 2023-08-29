package com.tomato.rabbitmq.message;

import com.tomato.jackson.utils.JacksonUtils;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

/**
 * 生产者
 *
 * @author lizhifu
 * @since 2023/5/11
 */
public abstract class AbstractProducerService implements ProducerService {

	@Resource
	private RabbitTemplate rabbitTemplate;

	/**
	 * 交换机
	 */
	private String exchange;

	/**
	 * 路由
	 */
	private String routingKey;

	@Override
	public void send(Object msg) {
		MessagePostProcessor messagePostProcessor = (message) -> {
			MessageProperties messageProperties = message.getMessageProperties();
			messageProperties.setMessageId(UUID.randomUUID().toString());
			messageProperties.setTimestamp(new Date());
			return message;
		};
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentEncoding("UTF-8");
		messageProperties.setContentType("text/plain");
		String data = JacksonUtils.toJson(msg);
		Message message = new Message(data.getBytes(StandardCharsets.UTF_8), messageProperties);
		rabbitTemplate.convertAndSend(this.exchange, this.routingKey, message, messagePostProcessor);
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}

}
