package com.tomato.redis.support;

import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.function.Function;

/**
 * 前缀 StringRedisSerializer
 *
 * @author lizhifu
 * @since 2023/7/16
 */
public class TenantPrefixStringRedisSerializer extends StringRedisSerializer {
    private final Function<String, String> prefixFunction;

    public TenantPrefixStringRedisSerializer(Function<String, String> prefixFunction) {
        this.prefixFunction = prefixFunction;
    }

    @Override
    public byte[] serialize(String key) {
        key = prefixFunction.apply(key);
        return super.serialize(key);
    }
}