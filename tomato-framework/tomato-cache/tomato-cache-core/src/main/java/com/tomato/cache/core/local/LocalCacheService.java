package com.tomato.cache.core.local;

/**
 * 本地缓存
 *
 * @author lizhifu
 * @since 2023/7/10
 */
public interface LocalCacheService<K, V> {

	/**
	 * 向缓存中添加数据
	 * @param key 缓存的key
	 * @param value 缓存的value
	 */
	void put(K key, V value);

	/**
	 * 根据key从缓存中查询数据
	 * @param key 缓存的key
	 * @return 缓存的value值
	 */
	V getIfPresent(Object key);

	/**
	 * 移除缓存中的数据
	 * @param key 缓存的key
	 */
	void remove(K key);

}
