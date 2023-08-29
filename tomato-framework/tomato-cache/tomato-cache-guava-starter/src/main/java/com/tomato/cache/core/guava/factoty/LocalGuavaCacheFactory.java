package com.tomato.cache.core.guava.factoty;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 基于Guava的本地缓存工厂类
 * <p>
 * Guava Cache是一个全内存的本地缓存实现，它提供了线程安全的实现机制。
 * <p>
 * 相比较ConcurrentMap，Guava Cache提供了更多的特性，比如：
 * <p>
 * 自动回收过期缓存
 * <p>
 * 缓存加载因子
 * <p>
 * 缓存命中率统计
 * <p>
 * 缓存项被回收通知
 * <p>
 * 支持多种缓存过期策略
 * <p>
 * 支持缓存项权重
 * <p>
 * 支持缓存项最大数量限制
 * <p>
 * 支持缓存项回收
 * <p>
 * 支持缓存项回收监听
 * <p>
 * 支持缓存项过期通知
 * <p>
 * 支持缓存项淘汰机制
 * <p>
 * 支持缓存项回收统计
 * <p>
 * 支持缓存项命中率统计
 * <p>
 * 支持缓存项加载时间统计
 * <p>
 * 支持缓存项加载异常统计
 * <p>
 * 支持缓存项总数统计
 * <p>
 * 支持缓存项平均加载时间统计
 *
 * <p>
 * 注意
 * </p>
 * <p>
 * 在refreshAfterWrite导致缓存失效时，并不会因为更新缓存而阻塞缓存数据的返回，只不过是返回老的数据
 * </p>
 * <p>
 * 不能缓存null
 * </p>
 * <p>
 * Guava Cache的缓存数据删除是在更新或写入时才会触发，没有单独的调度服务完成这一工作
 * </p>
 *
 * <p>
 * 缓存构建：
 * <p>
 * CacheBuilder.newBuilder() 创建缓存构建器
 * <p>
 * initialCapacity(int size) 设置缓存容器的初始容量
 * <p>
 * concurrencyLevel(int level) 设置并发级别，即同时可以写缓存的线程数
 * <p>
 * expireAfterAccess(long duration, TimeUnit unit) 设置缓存过期时间，写入后多久过期
 * <p>
 * expireAfterWrite(long duration, TimeUnit unit) 设置缓存过期时间，最后一次写入后多久过期
 * <p>
 * maximumSize(long size) 设置缓存最大容量，超过容量时会使用LRU算法移除缓存项
 * <p>
 * maximumWeight(long weight) 设置缓存最大权重，超过权重时会使用LRU算法移除缓存项
 * <p>
 * refreshAfterWrite(long duration, TimeUnit unit)
 * 设置缓存刷新时间，写入后多久刷新，在refreshAfterWrite导致缓存失效时， 并不会因为更新缓存而阻塞缓存数据的返回，只不过是返回老的数据
 * <p>
 * weakKeys() 设置缓存key为弱引用
 * <p>
 * weakValues() 设置缓存value为弱引用
 * <p>
 * softValues() 设置缓存value为软引用
 * <p>
 * removalListener(RemovalListener) 设置缓存移除监听器
 * <p>
 * build() 创建缓存对象
 *
 * <p>
 * 缓存加载：
 * <p>
 * load(k) 根据key查询，没有则返回null
 * <p>
 * loadAll(keys) 根据keys批量查询，没有则返回空map
 * <p>
 * reload(k,v) 根据key查询，没有则返回null，如果返回null则抛出异常
 * <p>
 * reloadAll(keys,values) 根据keys批量查询，没有则返回空map
 * <p>
 * reloadAll(keys) 根据keys批量查询，没有则返回空map
 * <p>
 * reloadAll() 批量查询，没有则返回空map
 * <p>
 * refresh(k) 根据key查询，没有则返回null，如果返回null则抛出异常
 * <p>
 * refreshAll(keys) 根据keys批量查询，没有则返回空map
 *
 * <p>
 * 缓存模式：
 * <p>
 * ManualCache 此时Cache相当于一个Map，对数据进行CRUD操作时，需要同步操作缓存Map; 高并发情况时，
 * 可以使用get(k,loader)读缓存，通过Cache锁机制，防止对系统资源（DB）的并发访问 通过put方法实现缓存的存入与更新；
 * <p>
 * CacheLoader 此时构建的是一个实现了Cache接口的LoadingCache，相比ManualCache，提供了缓存回填机制，即当缓存不存在时，
 * 会基于CacheLoader查询数据并将结果回填到缓存，
 * 在高并发时，可以有效地基于缓存锁减少对系统资源的调用。此时仅需要关注缓存的使用，缓存的更新与存入都是基于CacheLoader实现；
 *
 * <p>
 * 缓存获取方式：
 * <p>
 * get(k) 根据key查询，没有则触发load；如果load为空则抛出异常
 * <p>
 * getUnchecked(k) 缓存不存在或返回为null会抛出检查异常
 * <p>
 * get(k,loader) 根据key查询，没有则调用loader方法，且对结果缓存；如果loader返回null则抛出异常，此时不会调用默认的load方法
 * <p>
 * getIfPresent(k) 有缓存则返回，否则返回null，不会触发load
 *
 * <p>
 * 缓存更新:
 * <p>
 * put(k,v) 如果缓存已经存在，则会先进行一次删除
 *
 * <p>
 * 缓存删除:
 * <p>
 * invalidate(k) 删除缓存
 * <p>
 * 过期 通过配置的过期参数，比如expireAfterAccess、expireAfterWrite、refreshAfterWrite
 * <p>
 * 过载 当缓存数据量超过设置的最大值时，根据LRU算法进行删除
 * <p>
 * 引用 构建缓存时将键值设置为弱引用、软引用，基于GC机制来清理缓存
 *
 * <p>
 * 统计:
 * <p>
 * hitRate() 缓存命中率
 * <p>
 * hitMiss() 缓存失误率
 * <p>
 * loadCount() 加载次数
 * <p>
 * averageLoadPenalty() 加载新值的平均时间，单位为纳秒
 * <p>
 * evictionCount() 缓存项被回收的总数，不包括显式清除
 *
 * @author lizhifu
 * @since 2023/7/10
 */
public class LocalGuavaCacheFactory {

	public static <K, V> Cache<K, V> getLocalCache() {
		return CacheBuilder.newBuilder()
			.initialCapacity(200)
			.concurrencyLevel(5)
			.expireAfterWrite(300, TimeUnit.SECONDS)
			.build();
	}

	public static <K, V> Cache<K, V> getLocalCache(long duration) {
		return CacheBuilder.newBuilder()
			.initialCapacity(200)
			.concurrencyLevel(5)
			.expireAfterWrite(duration, TimeUnit.SECONDS)
			.build();
	}

	public static <K, V> Cache<K, V> getLocalCache(int initialCapacity, long duration) {
		return CacheBuilder.newBuilder()
			.initialCapacity(initialCapacity)
			.concurrencyLevel(5)
			.expireAfterWrite(duration, TimeUnit.SECONDS)
			.build();
	}

}
