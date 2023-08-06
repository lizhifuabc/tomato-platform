package com.tomato.tracing.thread;

import io.micrometer.core.instrument.MeterRegistry;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 自定义线程池
 *
 * @author lizhifu
 * @since 2023/8/6
 */
@Slf4j
public class TraceIdThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {
    private final MeterRegistry meterRegistry;

    public TraceIdThreadPoolTaskExecutor(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }
    @Override
    public void execute(@NotNull Runnable task) {
        super.execute(wrapTask(task));
    }

    private Runnable wrapTask(Runnable task) {
        Context context = Context.current();
        return () -> {
            try (Scope ignored = context.makeCurrent()) {
                task.run();
            } finally {
                // 使用 Micrometer 库记录一个指标（Metric），用于表示自定义线程池中执行的任务数量
                log.info("自定义线程池中执行的任务数量:{}", meterRegistry.counter("custom.thread-pool.tasks-executed").count());
                meterRegistry.counter("custom.thread-pool.tasks-executed").increment();
            }
        };
    }
}
