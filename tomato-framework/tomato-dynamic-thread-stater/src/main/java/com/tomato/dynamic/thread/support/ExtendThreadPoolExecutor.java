package com.tomato.dynamic.thread.support;

import com.tomato.dynamic.thread.support.runnable.DynamicRunnable;

import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 扩展线程池参数
 *
 * 执行任务前方法：beforeExecute(wt, task);
 * 执行任务：task.run();
 * 执行任务后方法：afterExecute(task, thrown);
 *
 * @author lizhifu
 * @date 2022/12/6
 */
public abstract class ExtendThreadPoolExecutor extends ThreadPoolExecutor {
    public ExtendThreadPoolExecutor(int corePoolSize,
                                    int maximumPoolSize,
                                    long keepAliveTime,
                                    BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
    }
    public ExtendThreadPoolExecutor(int corePoolSize,
                                     int maximumPoolSize,
                                     long keepAliveTime,
                                     BlockingQueue<Runnable> workQueue,
                                     ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue,threadFactory);
    }
    /**
     * 任务执行超时，单位（毫秒），仅供统计。
     */
    private long runTimeout;

    /**
     * 任务队列等待超时，单位（毫秒），仅供统计。
     */
    private long queueTimeout;

    /**
     * 计数运行超时任务。
     */
    private final AtomicInteger runTimeoutCount = new AtomicInteger();

    /**
     * 计数队列等待超时任务。
     */
    private final AtomicInteger queueTimeoutCount = new AtomicInteger();
    @Override
    public void execute(Runnable command) {
        if (runTimeout > 0 || queueTimeout > 0) {
            command = new DynamicRunnable(command);
        }
        super.execute(command);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        if (!(r instanceof DynamicRunnable)) {
            super.beforeExecute(t, r);
            return;
        }
        DynamicRunnable runnable = (DynamicRunnable) r;
        long currTime = System.currentTimeMillis();
        if (runTimeout > 0) {
            runnable.setStartExeTime(currTime);
        }
        if (queueTimeout > 0) {
            long waitTime = currTime - runnable.getSubmitTime();
            if (waitTime > queueTimeout) {

            }
        }
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        if (runTimeout > 0) {
            DynamicRunnable runnable = (DynamicRunnable) r;
            long runTime = System.currentTimeMillis() - runnable.getStartExeTime();
            if (runTime > runTimeout) {
                runTimeoutCount.incrementAndGet();
            }
        }
        super.afterExecute(r, t);
    }

    public long getRunTimeout() {
        return Optional.ofNullable(runTimeout).orElse(-1L);
    }

    public void setRunTimeout(long runTimeout) {
        this.runTimeout = Optional.ofNullable(runTimeout).orElse(-1L);
    }

    public long getQueueTimeout() {
        return Optional.ofNullable(queueTimeout).orElse(-1L);
    }

    public void setQueueTimeout(long queueTimeout) {
        this.queueTimeout = Optional.ofNullable(queueTimeout).orElse(-1L);
    }

    public int getRunTimeoutCount() {
        return runTimeoutCount.get();
    }

    public int getQueueTimeoutCount() {
        return queueTimeoutCount.get();
    }
}
