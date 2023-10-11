package com.tomato.common.pattern;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例
 *
 * @author lizhifu
 * @since 2023/10/11
 */
public enum Singleton {
	/**
	 * 单例
	 */
	INSTANCE;
	/**
	 * 单例Map
	 */
	private static final Map<String, Object> SINGLES = new ConcurrentHashMap<>();
	/**
	 * 单例
	 * @param clazz 类
	 * @param o 对象
	 */
	public void single(final Class<?> clazz, final Object o) {
		SINGLES.put(clazz.getName(), o);
	}
	/**
	 * 获取单例
	 * @param <T> 类型
	 * @param clazz 类
	 * @return 对象
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(final Class<T> clazz) {
		return (T) SINGLES.get(clazz.getName());
	}
}
