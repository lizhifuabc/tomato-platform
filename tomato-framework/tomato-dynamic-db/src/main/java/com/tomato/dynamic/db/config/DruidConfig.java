package com.tomato.dynamic.db.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.tomato.dynamic.db.constant.DbConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源信息配置,读取数据源配置信息并注册成bean
 *
 * @author lizhifu
 * @since 2023/8/12
 */
@Configuration
public class DruidConfig {

	@Bean(name = DbConstant.MASTER)
	@ConfigurationProperties("spring.datasource.druid.master")
	public DataSource masterDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean(name = DbConstant.SLAVE)
	@ConfigurationProperties("spring.datasource.druid.slave")
	public DataSource slaveDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean
	@Primary
	public DynamicDataSource dynamicDataSource() {
		Map<Object, Object> dataSourceMap = new HashMap<>(2);
		dataSourceMap.put(DbConstant.MASTER, masterDataSource());
		dataSourceMap.put(DbConstant.SLAVE, slaveDataSource());
		// 设置动态数据源
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		dynamicDataSource.setDefaultTargetDataSource(masterDataSource());
		dynamicDataSource.setTargetDataSources(dataSourceMap);
		// 将数据源信息备份在defineTargetDataSources中
		dynamicDataSource.setDefineTargetDataSources(dataSourceMap);
		return dynamicDataSource;
	}

}
