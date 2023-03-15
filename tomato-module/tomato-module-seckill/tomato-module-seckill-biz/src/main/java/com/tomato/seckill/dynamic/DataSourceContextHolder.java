package com.tomato.seckill.dynamic;

/**
 * 数据源上下文
 *
 * @author lizhifu
 * @since 2023/3/11
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
    public static void setDataSourceKey(String dataSourceKey) {
        contextHolder.set(dataSourceKey);
    }
    public static String getDataSourceKey() {
        return contextHolder.get();
    }
    public static void clearDataSourceKey() {
        contextHolder.remove();
    }
}