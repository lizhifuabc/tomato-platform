package com.tomato.rabbitmq.message;

import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

/**
 * 消费者
 *
 * @author lizhifu
 * @since 2023/5/11
 */
public interface ConsumerService extends ChannelAwareMessageListener {
}
