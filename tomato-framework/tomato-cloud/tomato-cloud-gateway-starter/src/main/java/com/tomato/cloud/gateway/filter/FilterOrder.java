package com.tomato.cloud.gateway.filter;

/**
 * 过滤器排序统一管理 {@link org.springframework.core.annotation.Order}
 * 值越小优先级越高
 * @author lizhifu
 * @since 2023/7/19
 */
public class FilterOrder {
    /**
     * 请求头灰度版本过滤器
     */
    public static final int HEADER_GRAY_VERSION = 0;
    /**
     * 权重灰度版本过滤器
     */
    public static final int WEIGHT_GRAY_VERSION = HEADER_GRAY_VERSION + 1;
}
