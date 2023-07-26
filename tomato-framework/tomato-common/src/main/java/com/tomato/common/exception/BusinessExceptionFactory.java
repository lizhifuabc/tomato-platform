package com.tomato.common.exception;

import com.tomato.common.resp.RespCode;

/**
 * 自定义异常工厂
 *
 * @author lizhifu
 * @since 2023/5/2
 */
public class BusinessExceptionFactory {
    /**
     * 异常
     *
     * @param respCode 错误码
     */
    public static void of(RespCode respCode) {
        of("错误码:【%s】,错误信息:【%s】", respCode.code(), respCode.msg());
    }

    /**
     * 异常
     *
     * @param message 异常信息
     */
    public static void of(String message) {
        throw new BusinessException(message);
    }

    /**
     * 异常
     *
     * @param message 异常信息
     * @param args    参数信息
     */
    public static void of(String message, Object... args) {
        throw new BusinessException(String.format(message, args));
    }
}
