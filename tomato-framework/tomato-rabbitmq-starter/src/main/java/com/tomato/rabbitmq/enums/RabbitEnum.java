package com.tomato.rabbitmq.enums;

import com.tomato.common.enums.BaseEnum;

/**
 *  队列，交换机。路由 绑定
 *
 * @author lizhifu
 * @since 2023/5/11
 */
public enum RabbitEnum implements BaseEnum<String> {
    /**
     * 队列名称
     */
    QUEUE("xxx.{}.queue", "队列名称"),
    /**
     * 交换机名称
     */
    EXCHANGE("xxx.{}.exchange", "交换机名称"),
    /**
     * 路由名称
     */
    ROUTER_KEY("xxx.{}.key", "路由名称"),
    ;
    RabbitEnum(String value, String desc) {
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
