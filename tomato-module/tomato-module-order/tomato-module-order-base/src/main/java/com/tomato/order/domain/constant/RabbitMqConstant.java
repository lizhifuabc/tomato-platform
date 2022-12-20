package com.tomato.order.domain.constant;

/**
 * rabbitmq 常量
 *
 * @author lizhifu
 * @since 2022/12/20
 */
public class RabbitMqConstant {
    /**
     * 延迟交换
     */
    public static final String ORDER_DELAY_EXCHANGE = "order.delay.exchange";
    /**
     * 延迟队列
     */
    public static final String ORDER_DELAY_QUEUE = "order.delay.queue";

    /**
     * 延迟队列
     */
    public static final String ORDER_DELAY_ROUTING_KEY = "order.delay.routing.key";
}
