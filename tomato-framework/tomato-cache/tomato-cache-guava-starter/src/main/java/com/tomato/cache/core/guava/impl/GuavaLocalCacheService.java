package com.tomato.cache.core.guava.impl;

import com.google.common.cache.Cache;
import com.tomato.cache.core.guava.factoty.LocalGuavaCacheFactory;
import com.tomato.cache.core.local.LocalCacheService;

import java.util.Optional;

/**
 * 基于Guava实现的本地缓存
 *
 * @author lizhifu
 * @since 2023/7/10
 */
public class GuavaLocalCacheService <K, V> implements LocalCacheService<K, V> {
    private final Cache<K, V> cache = LocalGuavaCacheFactory.getLocalCache();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public V getIfPresent(Object key) {
        Optional.ofNullable(key).orElseThrow(() -> new NullPointerException("key is null"));
        return cache.getIfPresent(key);
    }

    @Override
    public void remove(K key) {
        cache.invalidate(key);
    }
}
