package com.tomato.rabbitmq.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * ReturnCallback 退回模式(Exchange -> Queue)
 *
 * ReturnsCallback 消息机制用于处理一个不可路由的消息。 在某些情况下，如果我们在发送消息的时候，当前的 exchange 不存在或者指定路由 key 路由不到，
 * 这个时候我们需要监听这种不可达的消息
 * <p>
 * 配置参数需要设置：publisher-returns: true
 * </p>
 *
 * @author lizhifu
 * @since 2022/12/20
 */
@Slf4j
public abstract class RabbitTemplateReturnsCallback implements RabbitTemplate.ReturnsCallback {

	/**
	 * 队列确认
	 * @param returned ReturnedMessage 包含内容： message（消息体）、 replyCode（响应code）、
	 * replyText（响应内容）、 exchange（交换机）、 routingKey（队列）。
	 */
	@Override
	public void returnedMessage(ReturnedMessage returned) {
		log.info("返回消息回调:{} 应答代码:{} 回复文本:{} 交换器:{} 路由键:{}", returned.getMessage(), returned.getReplyCode(),
				returned.getReplyText(), returned.getExchange(), returned.getRoutingKey());
	}

}
