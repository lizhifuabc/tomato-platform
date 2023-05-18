package com.tomato.mybatis.util;

import java.util.*;

/**
 * 集合工具类
 * @author lizhifu
 */
public class CollectionUtils {


    /**
     * 判断集合是否为空
     *
     * @param collection 集合对象
     * @return 为空 true 否则false
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断一个集合中是否存在指定元素
     *
     * @param collection 集合对象
     * @param value      集合元素
     * @param <T>        集合类型
     * @return true:存在 否则不存在
     */
    public static <T> boolean contains(Collection<T> collection, T value) {
        return !isEmpty(collection) && collection.contains(value);
    }
}
