package com.tomato.notice.mq.config;

import com.tomato.notice.constant.RabbitMqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 通知超时延迟消费相关配置
 *
 * @author lizhifu
 * @since 2022/12/20
 */
@Configuration
public class NoticeMqDelayConfig {
    /**
     * RabbitMQ插件 rabbitmq_delayed_message_exchange
     * <a href="https://github.com/rabbitmq/rabbitmq-delayed-message-exchange">延时插件</a>
     * 延时交换机:延迟插件，本质不是一种交换机，只是一种插件
     * @return Exchange
     */
    @Bean
    public Exchange orderDelayExchange() {
        Map<String, Object> arguments = new HashMap<>(4);
        // 交换机类型(topic/direct/fanout)的属性
        // TOPIC 根据routing-key模糊匹配队列，*匹配任意一个字符，#匹配0个或多个字符
        arguments.put("x-delayed-type","topic");
        // 是否持久化 true
        // 当所有队绑定列均不在使用时，是否自动删除交换机 false
        return new CustomExchange(RabbitMqConstant.NOTICE_DELAY_EXCHANGE, "x-delayed-message", true, false, arguments);
    }
    /**
     * 创建一个队列
     * @return
     */
    @Bean
    public Queue orderDelayQueue() {
        // 是否持久化 true
        // 是否具有排他性，可多个消费者消费同一个队列 false
        // 当消费者均断开连接，是否自动删除队列 false
        return new Queue(RabbitMqConstant.NOTICE_DELAY_QUEUE, true, false, false);
    }
    /**
     * 绑定队列到自定义交换机
     * @return
     */
    @Bean
    public Binding bindingOrderDelay() {
        return new Binding(RabbitMqConstant.NOTICE_DELAY_QUEUE, Binding.DestinationType.QUEUE, RabbitMqConstant.NOTICE_DELAY_EXCHANGE, RabbitMqConstant.NOTICE_DELAY_ROUTING_KEY, null);
    }
}
