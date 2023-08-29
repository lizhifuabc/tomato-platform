package com.tomato.redis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 扩展 Redis 配置
 *
 * @author lizhifu
 * @since 2023/7/16
 */
@Data
@ConfigurationProperties(prefix = "spring.data.redis")
public class TencentRedisProperties {

	private boolean tenant = false;

}
