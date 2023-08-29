package com.tomato.redis.redisson.properties;

import lombok.Data;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redisson 配置 TODO
 *
 * @author lizhifu
 * @since 2023/7/12
 */
@Data
// @ConfigurationProperties(prefix = "spring.data.redis.redisson")
public class RedissonProperties {

	/**
	 * Redisson 使用模式
	 */
	public enum Mode {

		/**
		 * 单机
		 */
		SINGLE,
		/**
		 * 哨兵
		 */
		SENTINEL,
		/**
		 * 集群
		 */
		CLUSTER

	}

	/**
	 * 是否开启 Redisson
	 */
	private Boolean enabled = false;

	/**
	 * Redis 模式
	 */
	private Mode mode = Mode.SINGLE;

	/**
	 * 自定义配置文件路径
	 */
	// private String config;

	/**
	 * 单体配置
	 */
	private SingleServerConfig singleServerConfig;

	/**
	 * 集群配置
	 */
	private ClusterServersConfig clusterServersConfig;

	/**
	 * 哨兵配置
	 */
	private SentinelServersConfig sentinelServersConfig;

}
