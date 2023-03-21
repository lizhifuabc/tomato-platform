package com.tomato.web.common;


import com.tomato.web.util.BeanUtil;

/**
 * 基础 Controller
 *
 * @author lizhifu
 * @since 2022/12/18
 */
public class BaseController {
    public static <T> T copy(Object source, Class<T> target) {
        return BeanUtil.copy(source, target);
    }
}
