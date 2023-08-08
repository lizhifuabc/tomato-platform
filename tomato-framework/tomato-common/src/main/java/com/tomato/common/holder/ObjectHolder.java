package com.tomato.common.holder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对象持有者
 *
 * @author lizhifu
 * @since 2023/8/8
 */
public enum ObjectHolder {
    /**
     * 单例
     */
    INSTANCE;
    private static final int MAP_SIZE = 8;
    private static final Map<String, Object> OBJECT_MAP = new ConcurrentHashMap<>(MAP_SIZE);
    /**
     * 获取对象
     *
     * @param objectKey key
     * @return 对象
     */
    public Object getObject(String objectKey) {
        return OBJECT_MAP.get(objectKey);
    }
    /**
     * 获取对象
     *
     * @param clasz 类
     * @param <T>   泛型
     * @return 对象
     */
    public <T> T getObject(Class<T> clasz) {
        return clasz.cast(OBJECT_MAP.values().stream().filter(clasz::isInstance).findAny().orElseThrow(() -> new RuntimeException("找不到任何类的对象 " + clasz.getName())));
    }
    /**
     * 设置对象
     *
     * @param objectKey key
     * @param object  对象
     * @return Object 旧对象，如果没有旧对象，返回null
     */
    public Object setObject(String objectKey, Object object) {
        return OBJECT_MAP.put(objectKey, object);
    }
}
