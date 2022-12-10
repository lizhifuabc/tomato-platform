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
                                     BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, workQueue);
    }
    public DynamicThreadPoolExecutor(int corePoolSize,
                                     int maximumPoolSize,
                                     long keepAliveTime,
                                     BlockingQueue<Runnable> workQueue,
                                     ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, workQueue,threadFactory);
    }
}
