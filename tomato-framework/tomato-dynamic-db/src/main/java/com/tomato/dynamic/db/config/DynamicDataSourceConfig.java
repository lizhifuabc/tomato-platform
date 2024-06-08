package com.tomato.dynamic.db.config;

import com.tomato.dynamic.db.holder.DynamicDataSourceHolder;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * 数据源信息配置,读取数据源配置信息并注册成bean
 *
 * @author lizhifu
 * @since 2023/8/12
 */
@Component
@Slf4j
public class DynamicDataSourceConfig implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Primary
	@Bean(name = "dataSource")
	public DynamicDataSource dataSource(DynamicDataSourceProperties dynamicDataSourceProperties) {
		log.info("动态数据源初始化开始");
		Object master = DynamicDataSourceHolder.getTargetDatasource().get(dynamicDataSourceProperties.getMaster());

		Objects.requireNonNull(master, "master 数据源不存在");

		// 设置动态数据源
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		dynamicDataSource.setDefaultTargetDataSource(master);
		dynamicDataSource.setTargetDataSources(DynamicDataSourceHolder.getTargetDatasource());
		dynamicDataSource.setLenientFallback(dynamicDataSourceProperties.getLenientFallback());

		log.info("动态数据源初始化完成:{}",DynamicDataSourceHolder.getTargetDatasource());
		return dynamicDataSource;
	}
	@Override
	public void postProcessBeanDefinitionRegistry(@NotNull BeanDefinitionRegistry registry) throws BeansException {
		log.info("动态数据源bean初始化开始");
		// 加载 DynamicDataSourceProperties
		Binder binder = Binder.get(applicationContext.getEnvironment());
		DynamicDataSourceProperties dynamicDataSourceProperties = binder.bind(DynamicDataSourceProperties.PREFIX, DynamicDataSourceProperties.class).get();
		dynamicDataSourceProperties.getPool().forEach((key, value) -> {
			// 配置数据源名称 例如 hikari
			if (value.getName() == null) {
				value.setName(value.getPoolType().name());
			}
			// 加载 DataSource
			DataSource dataSource = value.initializeDataSourceBuilder().build();
			if (Objects.nonNull(value.getPoolType())) {
				// 加载数据源配置 绑定数据库连接池配置属性
				binder.bind(DynamicDataSourcePoolProperties.PREFIX + "." + key + "." + value.getPoolType(), Bindable.ofInstance(dataSource));
			}
			DynamicDataSourceHolder.getTargetDatasource().put(key, dataSource);
			// 注册 BeanDefinition
			AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(DataSource.class, () -> dataSource).getBeanDefinition();
			beanDefinition.setPrimary(false);
			registry.registerBeanDefinition(key, beanDefinition);
		});
		log.info("动态数据源bean初始化完成");
	}

	@Override
	public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
