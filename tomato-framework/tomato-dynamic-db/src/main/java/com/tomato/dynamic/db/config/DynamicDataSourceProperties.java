package com.tomato.dynamic.db.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

import static com.tomato.dynamic.db.config.DynamicDataSourceProperties.PREFIX;

/**
 * 动态数据源配置
 *
 * @author lizhifu
 * @since 2024/4/1
 */
@ConfigurationProperties(prefix = PREFIX)
@Data
public class DynamicDataSourceProperties {
	protected static final String PREFIX = "spring.dynamic";

	/**
	 * 是否开启
	 */
	private Boolean enabled = true;

	/**
	 * 当切换的数据库不存在时是否回退到默认数据库
	 */
	private Boolean lenientFallback = true;

	/**
	 * 主数据源
	 */
	private String master = "master";

	/**
	 * 多数据源连接池配置
	 */
	private Map<String, DynamicDataSourcePoolProperties> pool;
}
