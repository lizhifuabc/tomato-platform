package com.tomato.dynamic.db.config;

import com.tomato.dynamic.db.holder.DynamicDataSourceHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * 数据源信息配置,读取数据源配置信息并注册成bean
 *
 * @author lizhifu
 * @since 2024/6/8
 */
@Service
public class DynamicDataSourceService {

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * TODO 目前无法直接使用个，例如有N个服务器，每个服务器有一个数据源，需要循环调用
	 * TODO 每台服务器，目前的思路是，通过订阅的方式，监听数据源的变化，然后动态添加数据源
 	 * @param key
	 * @param dataSource
	 */
	public void addDataSource(String key, DataSource dataSource) {
		// 设置动态数据源
		DynamicDataSourceHolder.addTargetDatasource(key, dataSource);

		// 动态创建并注册数据源 bean
		AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(DataSource.class, () -> dataSource).getBeanDefinition();
		// 设置为非主 Bean
		beanDefinition.setPrimary(false);
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();
		registry.registerBeanDefinition(key, beanDefinition);
	}
}
