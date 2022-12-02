package com.tomato.web.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Spring 工具类
 * @author lizhifu
 */
@Slf4j
@Service
@Lazy(false)
public class SpringContextUtil implements ApplicationContextAware, DisposableBean {

	private static ApplicationContext applicationContext = null;

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 实现ApplicationContextAware接口, 注入Context到静态变量中.
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextUtil.applicationContext = applicationContext;
		log.info("初始化 SpringContextUtil 中的 ApplicationContext :{}",applicationContext);
	}

	/**
	 * 通过name获取 Bean.
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	/**
	 * 通过class获取Bean.
	 */
	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}
	/**
	 * 通过class获取Bean.
	 */
	public static <T> Map<String,T> getBeansOfType(Class<T> clazz) {
		return applicationContext.getBeansOfType(clazz);
	}
	/**
	 * 清除SpringContextHolder中的ApplicationContext为Null.
	 */
	public static void clearHolder() {
		log.info("清除 SpringContextUtil 中的 ApplicationContext :{}",applicationContext);
		applicationContext = null;
	}

	/**
	 * 发布事件
	 * @param event
	 */
	public static void publishEvent(ApplicationEvent event) {
		if (applicationContext == null) {
			return;
		}
		applicationContext.publishEvent(event);
	}

	/**
	 * 实现DisposableBean接口, 在Context关闭时清理静态变量.
	 */
	@Override
	public void destroy() {
		SpringContextUtil.clearHolder();
	}
}
