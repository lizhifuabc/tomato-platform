package com.tomato.rabbitmq.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 消息发送成功的回调,确认消息到达MQ服务器(生产者 ==>> Exchange 的回调确认) ConfirmCallback 确认模式 ConfirmCallback
 * 机制只确认消息是否到达exchange(交换器)，不保证消息可以路由到正确的queue; publisher-confirm-type 配置为 CORRELATED 或者
 * SIMPLE 时都会在此处进行回调。
 *
 * @author lizhifu
 * @since 2022/12/20
 */
@Slf4j
public abstract class RabbitTemplateConfirmReturn implements RabbitTemplate.ConfirmCallback {

	/**
	 * 交换机确认
	 * @param correlationData 回调的相关数据
	 * @param ack 消息投递到 broker 的状态，true表示成功
	 * @param cause 表示投递失败的原因
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		if (ack) {
			log.info("消息发送到exchange成功,correlationData:{}", correlationData);
		}
		else {
			log.error("消息发送到exchange失败,原因:" + cause);
		}
	}

}
