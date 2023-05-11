package com.tomato.rabbitmq.message;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;

/**
 * 重试处理器
 *
 * @author lizhifu
 * @since 2023/5/11
 */
public interface CustomRetryListener {
    /**
     * 最后一次重试失败回调
     * @param context 上下文
     * @param callback 回调
     * @param throwable 异常
     * @param <E> 异常
     * @param <T> 回调
     */
    public <E extends Throwable, T> void lastRetry(RetryContext context, RetryCallback<T,E> callback, Throwable throwable);

    /**
     * 每次失败的回调
     * @param context 上下文
     * @param callback 回调
     * @param throwable 异常
     * @param <E> 异常
     * @param <T> 回调
     */
    public <E extends Throwable, T> void onRetry(RetryContext context, RetryCallback<T,E> callback, Throwable throwable);
}
