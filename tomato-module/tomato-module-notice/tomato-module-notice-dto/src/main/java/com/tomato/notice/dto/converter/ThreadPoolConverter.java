package com.tomato.notice.dto.converter;

import com.tomato.notice.dto.resp.ThreadPoolStatsResp;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池信息转换
 *
 * @author lizhifu
 * @since 2023/5/15
 */
public class ThreadPoolConverter {
    public static ThreadPoolStatsResp convert(ThreadPoolExecutor executor) {
        return ThreadPoolStatsResp.builder()
                .poolName(executor.getClass().getSimpleName())
                .corePoolSize(executor.getCorePoolSize())
                .maximumPoolSize(executor.getMaximumPoolSize())
                .poolSize(executor.getPoolSize())
                .activeCount(executor.getActiveCount())
                .largestPoolSize(executor.getLargestPoolSize())
                .queueType(executor.getQueue().getClass().getSimpleName())
                .queueCapacity(getQueueCapacity(executor))
                .queueSize(executor.getQueue().size())
                .queueRemainingCapacity(executor.getQueue().remainingCapacity())
                .taskCount(executor.getTaskCount())
                .completedTaskCount(executor.getCompletedTaskCount())
                .waitTaskCount(executor.getQueue().size())
                .rejectHandlerName(executor.getRejectedExecutionHandler().getClass().getSimpleName())
                .build();
    }
    public static int getQueueCapacity(ThreadPoolExecutor executor) {
        int capacity = executor.getQueue().size() + executor.getQueue().remainingCapacity();
        return capacity < 0 ? Integer.MAX_VALUE : capacity;
    }
}
