package com.tomato.order.infrastructure.holder;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * ApplicationContextAware TODO 提取到共用方法
 *
 * @author lizhifu
 * @since 2023/8/5
 */
public class ApplicationContextHolder implements ApplicationContextAware {

	private static ApplicationContext CONTEXT;

	@Override
	public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
		ApplicationContextHolder.CONTEXT = applicationContext;
	}

	public static <T> T getBean(Class<T> clazz) {
		return CONTEXT.getBean(clazz);
	}

	public static <T> T getBean(String name, Class<T> clazz) {
		return CONTEXT.getBean(name, clazz);
	}

	public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
		return CONTEXT.getBeansOfType(clazz);
	}

	public static <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) {
		return CONTEXT.findAnnotationOnBean(beanName, annotationType);
	}

	public static ApplicationContext getInstance() {
		return CONTEXT;
	}

}
