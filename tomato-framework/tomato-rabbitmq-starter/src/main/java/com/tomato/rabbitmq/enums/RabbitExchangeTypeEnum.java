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
     * 根据routing-key精准匹配队列(最常使用)
     */
    DIRECT("DIRECT", "直连交换机"),

    /**
     * 主题交换机
     * <p>
     * 根据routing-key模糊匹配队列，*匹配任意一个字符，#匹配0个或多个字符
     */
    TOPIC("TOPIC", "主题交换机"),
    /**
     * 扇形交换机
     * <p>
     * 直接分发给所有绑定的队列，忽略routing-key,用于广播消息
     */
    FANOUT("FANOUT", "扇形交换机"),
    /**
     * 头交换机
     * <p>
     * 类似直连交换机，不同于直连交换机的路由规则建立在头属性上而不是routing-key(使用较少)
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
