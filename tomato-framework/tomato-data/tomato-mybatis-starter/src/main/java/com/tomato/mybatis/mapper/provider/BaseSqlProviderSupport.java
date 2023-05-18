package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sql provider 抽象类
 *
 * @author lizhifu
 * @since 2023/5/18
 */
@Slf4j
public abstract class BaseSqlProviderSupport {
    /**
     * key -> mapper class   value -> tableInfo
     */
    private static final Map<Class<?>, TableInfo> TABLE_CACHE = new ConcurrentHashMap<>(128);
    /**
     * key -> {@link #getCacheKey}   value -> SQL
     */
    static final Map<String, String> SQL_CACHE = new ConcurrentHashMap<>(128);
    /**
     * 获取表信息结构
     *
     * @param context  provider context
     * @return  表基本信息
     */
    protected TableInfo tableInfo(ProviderContext context) {
        log.info("context:{}",context.getMapperType());
        // 如果不存在则创建
        return TABLE_CACHE.computeIfAbsent(context.getMapperType(), TableInfo::of);
    }
    static String getCacheKey(ProviderContext context) {
        return context.getMapperType().getSimpleName() + ":" + context.getMapperMethod().getName();
    }
}
