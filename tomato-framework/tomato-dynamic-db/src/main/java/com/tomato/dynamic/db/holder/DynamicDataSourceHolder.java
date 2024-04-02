package com.tomato.dynamic.db.holder;

import lombok.extern.slf4j.Slf4j;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

/**
 * 数据源切换
 *
 * @author lizhifu
 * @since 2023/8/10
 */
@Slf4j
public class DynamicDataSourceHolder {

	/**
	 * 保存动态数据源名称
	 */
	private static final ThreadLocal<Deque<String>> DYNAMIC_DATASOURCE_KEY = new ThreadLocal<>();

	/**
	 * 设置/切换数据源，决定当前线程使用哪个数据源
	 */
	public static void setDynamicDataSourceKey(String key) {
		log.info("数据源切换为：{}", key);
		Deque<String> list = DYNAMIC_DATASOURCE_KEY.get();
		if (Objects.isNull(list)){
			list = new LinkedList<>();
		}
		list.addFirst(key);
		DYNAMIC_DATASOURCE_KEY.set(list);
	}

	/**
	 * 获取动态数据源
	 */
	public static String getDynamicDataSourceKey() {
		Deque<String> list = DYNAMIC_DATASOURCE_KEY.get();
		if (Objects.isNull(list) || list.isEmpty()) {
			return null;
		}
		return list.getFirst();
	}

	/**
	 * 移除数据源
	 */
	public static void removeDynamicDataSourceKey() {
		log.info("移除数据源：{}", DYNAMIC_DATASOURCE_KEY.get());
		Deque<String> list = DYNAMIC_DATASOURCE_KEY.get();
		if (Objects.isNull(list) || list.isEmpty()) {
			DYNAMIC_DATASOURCE_KEY.remove();
			return;
		}
		if (list.size() == 1) {
			list.clear();
			DYNAMIC_DATASOURCE_KEY.remove();
		}else {
			list.removeFirst();
		}
	}
}
