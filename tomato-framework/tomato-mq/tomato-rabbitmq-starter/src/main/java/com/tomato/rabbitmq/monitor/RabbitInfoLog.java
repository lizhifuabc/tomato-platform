package com.tomato.rabbitmq.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;

/**
 * @author lizhifu
 */
public class RabbitInfoLog implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(RabbitInfoLog.class);

    private final RabbitTemplate rabbitTemplate;

    private final RabbitProperties rabbitProperties;

    public RabbitInfoLog(RabbitTemplate rabbitTemplate, RabbitProperties rabbitProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitProperties = rabbitProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AbstractConnectionFactory connectionFactory = (AbstractConnectionFactory) rabbitTemplate.getConnectionFactory();
        logger.info("[RabbitMQ]连接地址: {}", rabbitProperties.getHost());
        logger.info("[RabbitMQ]连接端口: {}", rabbitProperties.getPort());
        logger.info("[RabbitMQ]连接用户名: {}", rabbitProperties.getUsername());
        logger.info("[RabbitMQ]连接密码: {}", rabbitProperties.getPassword());
        logger.info("[RabbitMQ]连接超时时间: {}", rabbitProperties.getConnectionTimeout());
        logger.info("[RabbitMQ]RPC连接超时时间: {}", rabbitProperties.getChannelRpcTimeout());
        logger.info("[RabbitMQ]连接地址: {}", rabbitProperties.getAddresses());
        logger.info("[RabbitMQ]连接虚拟主机: {}", rabbitProperties.getVirtualHost());
        logger.info("[RabbitMQ]请求心跳检测间隔: {}", rabbitProperties.getRequestedHeartbeat());
        logger.info("[RabbitMQ]信道缓存大小: {}", rabbitProperties.getCache().getChannel().getSize());
        logger.info("[RabbitMQ]信道获取超时时间: {}", rabbitProperties.getCache().getChannel().getCheckoutTimeout());

        // 启用发布者确认模式后，当消息发送到 Exchange 时，如果 Exchange 发生错误，将无法将消息路由到任何 Queue，
        // 此时将会返回一个 Basic.Return 消息，可以通过监听 ReturnCallback 接口来获取该消息。
        logger.info("[RabbitMQ]是否为发布返回模式: {}", rabbitProperties.isPublisherReturns());
        // 在启用发布确认模式后，当消息被成功路由到 Exchange 中时，RabbitMQ 会发送一个 Basic.Ack 消息给生产者。
        // 如果无法将消息路由到任何队列，则会发送 Basic.Nack 消息。
        // 简单发布确认模式下:
        // 发布者只需等待 Broker 的确认消息，而不需要等待所有消费者都接收到了消息。这种模式下，若 Broker 返回了确认消息，
        // 那么就表示消息已经成功发送到了 Exchange 中，但并不能保证该消息已被所有消费者接收到。
        // 普通确认模式:
        // 发布者需要等待所有消费者都接收到了消息并发送了确认消息，才认为消息发送成功。这种模式下，确认模式的开销比较大，但是可以保证消息不会丢失。
        logger.info("[RabbitMQ]发布确认模式: {}", rabbitProperties.getPublisherConfirmType());

        logger.info("[RabbitMQ]RabbitTemplate已初始化，连接工厂为: {}", connectionFactory);
        logger.info("[RabbitMQ]RabbitTemplate是否使用发布者连接进行关联: {}", rabbitTemplate.isUsePublisherConnection() ? "是" : "否");
        logger.info("[RabbitMQ]RabbitTemplate的消息转换器为: {}", rabbitTemplate.getMessageConverter());
        logger.info("[RabbitMQ]RabbitTemplate的接收后处理器为: {}", rabbitTemplate.getAfterReceivePostProcessors());
        logger.info("[RabbitMQ]RabbitTemplate是否开启事务: {}", rabbitTemplate.isChannelTransacted() ? "是" : "否");
        logger.info("[RabbitMQ]RabbitTemplate是否使用发布者连接进行关联: {}", rabbitTemplate.isUsePublisherConnection() ? "是" : "否");
        logger.info("[RabbitMQ]RabbitTemplate的接收后处理器为: {}", rabbitTemplate.getAfterReceivePostProcessors());
    }
}
