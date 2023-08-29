package com.tomato.rabbitmq.properties;

import com.tomato.rabbitmq.enums.RabbitExchangeTypeEnum;
import lombok.Data;

import java.util.Map;

/**
 * rabbitmq配置
 *
 * @author lizhifu
 * @since 2023/5/11
 */
@Data
public class RabbitInfo {

	/**
	 * 路由Key
	 */
	private String routingKey;

	/**
	 * 生产者 service 名称
	 */
	private String producer;

	/**
	 * 消费者 service 名称
	 */
	private String consumer;

	/**
	 * 自动确认,默认false
	 */
	private Boolean autoAck = false;

	/**
	 * 队列信息
	 */
	private Queue queue;

	/**
	 * 交换机信息
	 */
	private Exchange exchange;

	/**
	 * 重试 bean name
	 */
	private String retry;

	/**
	 * 并发消费者数量
	 */
	private int concurrentConsumers = 20;

	/**
	 * 最大并发消费者数量
	 */
	private int maxConcurrentConsumers = 100;

	/**
	 * 消息预取（prefetch）：可以配置每个消费者从 RabbitMQ 中预先获取的消息数量，以控制消息的批量处理。
	 * 限制每次只消费一个(一个线程)，上面配置5，也就是能一次接收5个。
	 */
	private int prefetchCount = 20;

	/**
	 * 交换机信息
	 */
	@Data
	public static class Exchange {

		/**
		 * 交换机类型 默认主题交换机
		 */
		private RabbitExchangeTypeEnum type = RabbitExchangeTypeEnum.TOPIC;

		/**
		 * 交换机名称
		 */
		private String name;

		/**
		 * 是否持久化 默认true持久化，重启消息不会丢失
		 */
		private boolean durable = true;

		/**
		 * 当所有队绑定列均不在使用时，是否自动删除交换机 默认false，不自动删除
		 */
		private boolean autoDelete = false;

		/**
		 * 交换机其他参数
		 */
		private Map<String, Object> arguments;

	}

	/**
	 * 队列信息
	 */
	@Data
	public static class Queue {

		/**
		 * 队列名称
		 */
		private String name;

		/**
		 * 是否持久化,默认true持久化，重启消息不会丢失
		 */
		private boolean durable = true;

		/**
		 * 是否具有排他性,默认false，可多个消费者消费同一个队列
		 */
		private boolean exclusive = false;

		/**
		 * 当消费者均断开连接，是否自动删除队列 默认false,不自动删除，避免消费者断开队列丢弃消息
		 */
		private boolean autoDelete = false;

		/**
		 * 绑定死信队列的交换机名称
		 */
		private String deadLetterExchange;

		/**
		 * 绑定死信队列的路由key
		 */
		private String deadLetterRoutingKey;

		/**
		 * 交换机其他参数
		 */
		private Map<String, Object> arguments;

	}

}
