package com.tomato.order.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * SnowProperties
 *
 * @author lizhifu
 * @since 2023/1/3
 */
@Component
@Getter
@PropertySource(value = {"${tomato.init.snow}"}, encoding = "utf-8")
public class SnowProperties {
    /**
     * 所属机房id
     */
    @Value("${tomato.init.workerId}")
    private long datacenterId;
    /**
     * 所属机器id
     */
    @Value("${tomato.init.datacenterId}")
    private long workerId;
}
