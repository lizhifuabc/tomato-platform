package com.tomato.pay.infrastructure.mq.config;

import com.tomato.pay.domain.constants.PayMqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定义 MQ 的配置和 Binding 设置
 *
 * @author lizhifu
 * @since 2023/4/12
 */
@Configuration
public class RabbitMQConfig {
    /**
     * 将消息广播到绑定到它的所有队列中，不管路由键是什么。也就是说，一个 FanoutExchange 绑定到多个 Queue，
     * 当向该 Exchange 发送消息时，它会将消息发送到所有绑定的 Queue 中，忽略消息的路由键。因此，FanoutExchange 适用于消息广播的场景。
     * @return FanoutExchange
     */
    @Bean
    public FanoutExchange payExchange() {
        return new FanoutExchange(PayMqConstant.PAY_RESULT_EXCHANGE);
    }

    @Bean
    public Queue orderQueue() {
        return new Queue(PayMqConstant.PAY_RESULT_ORDER_QUEUE);
    }

    @Bean
    public Queue noticeQueue() {
        return new Queue(PayMqConstant.PAY_RESULT_NOTICE_QUEUE);
    }

    @Bean
    public Binding orderBinding() {
        return BindingBuilder.bind(orderQueue()).to(payExchange());
    }
    @Bean
    public Binding noticeBinding() {
        return BindingBuilder.bind(noticeQueue()).to(payExchange());
    }
}
