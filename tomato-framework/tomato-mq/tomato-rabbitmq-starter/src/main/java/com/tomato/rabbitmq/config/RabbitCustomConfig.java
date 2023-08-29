package com.tomato.rabbitmq.config;

import com.tomato.rabbitmq.enums.RabbitEnum;
import com.tomato.rabbitmq.enums.RabbitExchangeTypeEnum;
import com.tomato.rabbitmq.message.*;
import com.tomato.rabbitmq.properties.RabbitInfo;
import com.tomato.rabbitmq.properties.RabbitProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StopWatch;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 自定义 rabbitmq 配置
 *
 * @author lizhifu
 * @since 2023/5/23
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitCustomConfig implements SmartInitializingSingleton {

	@Resource
	private ConnectionFactory connectionFactory;

	@Resource
	private AmqpAdmin amqpAdmin;

	@Resource
	private RabbitProperties rabbitProperties;

	@Resource
	private ApplicationContext applicationContext;

	@Override
	public void afterSingletonsInstantiated() {
		StopWatch stopWatch = new StopWatch("初始化MQ配置");
		stopWatch.start();
		log.info("自定义 rabbitmq 配置：开始");
		List<RabbitInfo> rabbitInfoList = rabbitProperties.getRabbitInfoList();
		if (Objects.isNull(rabbitInfoList)) {
			return;
		}
		// 遍历配置
		for (RabbitInfo rabbitInfo : rabbitInfoList) {
			Queue queue = genQueue(rabbitInfo);
			Exchange exchange = genQueueExchange(rabbitInfo);
			queueBindExchange(queue, exchange, rabbitInfo);
			bindProducer(rabbitInfo);
			try {
				bindConsumer(queue, exchange, rabbitInfo);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		stopWatch.stop();
		log.info("自定义 rabbitmq 配置：结束: {}ms", stopWatch.getTotalTimeMillis());
	}

	/**
	 * 绑定生产者
	 * @param rabbitInfo 配置信息
	 */
	private void bindProducer(RabbitInfo rabbitInfo) {
		log.info("自定义 rabbitmq 配置：绑定生产者: {}", rabbitInfo.getProducer());
		if (Objects.isNull(rabbitInfo.getProducer())) {
			return;
		}
		AbstractProducerService producerService = applicationContext.getBean(rabbitInfo.getProducer(),
				AbstractProducerService.class);
		producerService.setExchange(rabbitInfo.getExchange().getName());
		producerService.setRoutingKey(rabbitInfo.getRoutingKey());
	}

	/**
	 * 绑定消费者
	 * @param queue 队列
	 * @param exchange 交换机
	 * @param rabbitInfo 配置信息
	 */
	private void bindConsumer(Queue queue, Exchange exchange, RabbitInfo rabbitInfo) throws Exception {
		log.info("自定义 rabbitmq 配置：绑定消费者: {}：监听：{}", rabbitInfo.getConsumer(), rabbitInfo.getRetry());
		if (Objects.isNull(rabbitInfo.getConsumer())) {
			return;
		}
		ConsumerService consumerService = (ConsumerService) applicationContext.getBean(rabbitInfo.getConsumer());
		CustomRetryListener customRetryListener = Optional.ofNullable(rabbitInfo.getRetry())
			.map(re -> applicationContext.getBean(re, CustomRetryListener.class))
			.orElse(null);
		ConsumerContainerFactory factory = ConsumerContainerFactory.builder()
			.connectionFactory(connectionFactory)
			.queue(queue)
			.exchange(exchange)
			.consumer(consumerService)
			.retryListener(customRetryListener)
			.autoAck(rabbitInfo.getAutoAck())
			.concurrentConsumers(rabbitInfo.getConcurrentConsumers())
			.maxConcurrentConsumers(rabbitInfo.getMaxConcurrentConsumers())
			.prefetchCount(rabbitInfo.getPrefetchCount())
			.amqpAdmin(amqpAdmin)
			.build();
		SimpleMessageListenerContainer container = factory.getObject();
		assert container != null;
		container.start();
	}

	/**
	 * 队列绑定交换机
	 * @param queue 队列
	 * @param exchange 交换机
	 * @param rabbitInfo 配置信息
	 */
	private void queueBindExchange(Queue queue, Exchange exchange, RabbitInfo rabbitInfo) {
		String queueName = rabbitInfo.getQueue().getName();
		String exchangeName = rabbitInfo.getExchange().getName();
		log.info("自定义 rabbitmq 配置：队列绑定交换机: 队列: {}, 交换机: {}", queueName, exchangeName);
		rabbitInfo.setRoutingKey(String.format(RabbitEnum.ROUTER_KEY.getValue(), rabbitInfo.getRoutingKey()));
		String routingKey = rabbitInfo.getRoutingKey();
		Binding binding = new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName, routingKey, null);
		amqpAdmin.declareQueue(queue);
		amqpAdmin.declareExchange(exchange);
		amqpAdmin.declareBinding(binding);
	}

	/**
	 * 创建交换机
	 * @param rabbitInfo 配置信息
	 * @return 交换机
	 */
	private Exchange genQueueExchange(RabbitInfo rabbitInfo) {
		RabbitInfo.Exchange exchange = rabbitInfo.getExchange();
		RabbitExchangeTypeEnum exchangeType = exchange.getType();
		exchange.setName(String.format(RabbitEnum.EXCHANGE.getValue(), exchange.getName()));
		String exchangeName = exchange.getName();
		Boolean isDurable = exchange.isDurable();
		Boolean isAutoDelete = exchange.isAutoDelete();
		Map<String, Object> arguments = exchange.getArguments();
		return getExchangeByType(exchangeType, exchangeName, isDurable, isAutoDelete, arguments);
	}

	/**
	 * 根据类型生成交换机
	 * @param exchangeType 交换机类型
	 * @param exchangeName 交换机名称
	 * @param isDurable 是否持久化
	 * @param isAutoDelete 是否自动删除
	 * @param arguments 参数
	 * @return 交换机
	 */
	private Exchange getExchangeByType(RabbitExchangeTypeEnum exchangeType, String exchangeName, Boolean isDurable,
			Boolean isAutoDelete, Map<String, Object> arguments) {
		return switch (exchangeType) {
			// 直连交换机
			case DIRECT -> new DirectExchange(exchangeName, isDurable, isAutoDelete, arguments);
			// 主题交换机
			case TOPIC -> new TopicExchange(exchangeName, isDurable, isAutoDelete, arguments);
			// 扇形交换机
			case FANOUT -> new FanoutExchange(exchangeName, isDurable, isAutoDelete, arguments);
			// 头交换机
			case HEADERS -> new HeadersExchange(exchangeName, isDurable, isAutoDelete, arguments);
		};
	}

	/**
	 * 创建队列
	 * @param rabbitInfo 配置信息
	 * @return 队列
	 */
	private Queue genQueue(RabbitInfo rabbitInfo) {
		RabbitInfo.Queue queue = rabbitInfo.getQueue();
		queue.setName(String.format(RabbitEnum.QUEUE.getValue(), queue.getName()));
		log.info("自定义 rabbitmq 配置：初始化队列: {}", queue.getName());
		Map<String, Object> arguments = queue.getArguments();
		if (Objects.isNull(arguments)) {
			arguments = new HashMap<>(16);
		}
		// 转换ttl的类型为long
		if (arguments.containsKey("x-message-ttl")) {
			arguments.put("x-message-ttl", Long.parseLong(arguments.get("x-message-ttl").toString()));
		}

		// 绑定死信队列
		String deadLetterExchange = queue.getDeadLetterExchange();
		String deadLetterRoutingKey = queue.getDeadLetterRoutingKey();
		if (isNotBlank(deadLetterExchange) && isNotBlank(deadLetterRoutingKey)) {
			deadLetterExchange = String.format(RabbitEnum.EXCHANGE.getValue(), deadLetterExchange);
			deadLetterRoutingKey = String.format(RabbitEnum.ROUTER_KEY.getValue(), deadLetterRoutingKey);
			arguments.put("x-dead-letter-exchange", deadLetterExchange);
			arguments.put("x-dead-letter-routing-key", deadLetterRoutingKey);
			log.info("自定义 rabbitmq 配置：绑定死信队列: 交换机: {}, 路由: {}", deadLetterExchange, deadLetterRoutingKey);
		}
		return new Queue(queue.getName(), queue.isDurable(), queue.isExclusive(), queue.isAutoDelete(), arguments);
	}

}
