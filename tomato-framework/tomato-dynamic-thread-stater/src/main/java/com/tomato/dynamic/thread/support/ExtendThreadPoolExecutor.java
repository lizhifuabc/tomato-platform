package com.tomato.dynamic.thread.support;

import com.tomato.dynamic.thread.support.runnable.DynamicRunnable;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
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
     * 线程池名称
     */
    protected String threadPoolName;
    /**
     * 执行超时，单位（毫秒）
     */
    private long runTimeout;

    /**
     * 等待超时，单位（毫秒）
     */
    private long queueTimeout;

    /**
     * 执行超时数量
     */
    private final AtomicInteger runTimeoutCount = new AtomicInteger();

    /**
     * 等待超时数量
     */
    private final AtomicInteger queueTimeoutCount = new AtomicInteger();
    @Override
    public void execute(Runnable command) {
        if (runTimeout > 0 || queueTimeout > 0) {
            // 记录任务提交时间
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
            // 记录任务开始执行时间
            runnable.setStartExeTime(currTime);
        }
        if (queueTimeout > 0) {
            // 任务开始执行时间 - 任务创建(提交)时间
            long waitTime = currTime - runnable.getSubmitTime();
            if (waitTime > queueTimeout) {
                log.error("{} execute queue timeout waitTime: {}ms", this.getThreadPoolName(),waitTime);
            }
        }
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        if (runTimeout > 0) {
            DynamicRunnable runnable = (DynamicRunnable) r;
            // 任务执行总时间：任务结束执行时间 - 任务开始执行时间
            long runTime = System.currentTimeMillis() - runnable.getStartExeTime();
            if (runTime > runTimeout) {
                runTimeoutCount.incrementAndGet();
                log.error("{} execute, run timeout runTime: {}ms", this.getThreadPoolName(), runTime);
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

    public String getThreadPoolName() {
        return Optional.ofNullable(threadPoolName).orElse("DynamicThreadPool");
    }

    public void setThreadPoolName(String threadPoolName) {
        this.threadPoolName = Optional.ofNullable(threadPoolName).orElse("DynamicThreadPool");
    }
}
