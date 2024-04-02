package com.tomato.dynamic.db.config;

import com.tomato.dynamic.db.config.db.HikariConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

/**
 * 多数据源连接池配置
 *
 * @author lizhifu
 * @since 2024/4/2
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class DynamicDataSourcePoolProperties extends DataSourceProperties {
	protected static final String PREFIX = DynamicDataSourceProperties.PREFIX + ".pool";
	/**
	 * 配置的类型
	 */
	private PoolType poolType;
	/**
	 * hikari 数据连接池配置
	 */
	private HikariConfig hikari;

	public void setHikari(HikariConfig hikari) {
		this.hikari = hikari;
		this.poolType = PoolType.hikari;
	}

	public enum PoolType {
		/**
		 * hikari 数据连接池
		 */
		hikari,
	}
}
