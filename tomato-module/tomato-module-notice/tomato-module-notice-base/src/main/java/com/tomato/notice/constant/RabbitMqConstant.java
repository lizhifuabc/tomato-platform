package com.tomato.notice.constant;

/**
 * rabbitmq 常量
 *
 * @author lizhifu
 * @since 2023/1/5
 */
public class RabbitMqConstant {
    /**
     * 延迟交换
     */
    public static final String NOTICE_DELAY_EXCHANGE = "order.delay.exchange";
    /**
     * 延迟队列
     */
    public static final String NOTICE_DELAY_QUEUE = "order.delay.queue";

    /**
     * 延迟队列
     */
    public static final String NOTICE_DELAY_ROUTING_KEY = "order.delay.routing.key";
}
