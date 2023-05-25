package com.tomato.rabbitmq.enums;

import com.tomato.common.enums.BaseEnum;

/**
 * 交换机类型枚举
 *
 * @author lizhifu
 * @since 2023/5/11
 */
public enum RabbitExchangeTypeEnum implements BaseEnum<String> {
    /**
     * 直连交换机
     * <p>
     * 1. 根据消息的路由键将消息发送到与该路由键完全匹配的队列。
     * 2. 只有路由键与队列绑定的路由键完全相同时，消息才会被路由到该队列。
     */
    DIRECT("DIRECT", "直连交换机"),

    /**
     * 主题交换机
     * <p>
     * 1. 根据消息的路由键和绑定键的模式进行匹配。
     * 2. 绑定键可以使用通配符进行匹配，支持通配符符号 *（匹配一个单词）和 #（匹配零个或多个单词）。
     */
    TOPIC("TOPIC", "主题交换机"),
    /**
     * 扇形交换机
     * <p>
     * 1. 将消息广播到所有与交换机绑定的队列，忽略消息的路由键。
     * 2. 当多个消费者需要接收相同的消息时，可以使用扇形交换机。
     */
    FANOUT("FANOUT", "扇形交换机"),
    /**
     * 头交换机
     * <p>
     * 1. 使用消息的头部信息进行匹配，而不考虑路由键的值。
     * 2. 通过设置消息的头部信息和队列的绑定参数进行匹配。
     */
    HEADERS("HEADERS", "头交换机"),

    ;
    RabbitExchangeTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
    private final String value;
    private final String desc;
    @Override
    public String getDesc() {
        return desc;
    }
    @Override
    public String getValue() {
        return value;
    }
}
