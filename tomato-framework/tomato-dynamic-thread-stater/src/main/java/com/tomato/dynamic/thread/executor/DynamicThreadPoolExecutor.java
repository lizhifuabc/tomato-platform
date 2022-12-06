package com.tomato.dynamic.thread.executor;

import com.tomato.dynamic.thread.support.ExtendThreadPoolExecutor;

import java.util.concurrent.*;

/**
 * 动态线程池
 *
 * @author lizhifu
 * @date 2022/12/4
 */
public class DynamicThreadPoolExecutor extends ExtendThreadPoolExecutor {
    public DynamicThreadPoolExecutor(int corePoolSize,
                                     int maximumPoolSize,
                                     long keepAliveTime,
                                     TimeUnit unit,
                                     BlockingQueue<Runnable> workQueue,
                                     ThreadFactory threadFactory,
                                     RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }
}
