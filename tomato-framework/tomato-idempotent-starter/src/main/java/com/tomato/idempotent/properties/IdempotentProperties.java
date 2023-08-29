package com.tomato.idempotent.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 幂等属性配置
 *
 * @author lizhifu
 * @since 2023/4/11
 */
@ConfigurationProperties(prefix = IdempotentProperties.PREFIX)
public class IdempotentProperties {

	public static final String PREFIX = "tomato.idempotent";

}
