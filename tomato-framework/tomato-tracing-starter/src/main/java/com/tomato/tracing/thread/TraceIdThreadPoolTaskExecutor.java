package com.tomato.tracing.thread;

import io.micrometer.core.instrument.MeterRegistry;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池
 *
 * @author lizhifu
 * @since 2023/8/6
 */
@Slf4j
public class TraceIdThreadPoolTaskExecutor extends ThreadPoolExecutor {

	private final MeterRegistry meterRegistry;

	/**
	 * 构造函数 传入 MeterRegistry 不定长线程池
	 * @param meterRegistry 注册表
	 */
	public TraceIdThreadPoolTaskExecutor(MeterRegistry meterRegistry) {
		super(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
		this.meterRegistry = meterRegistry;
	}

	@Override
	public void execute(@NotNull Runnable task) {
		super.execute(wrapTask(task));
	}

	private Runnable wrapTask(Runnable task) {
		Context context = Context.current();
		return () -> {
			// 使用 OpenTelemetry 的 Context API，将当前线程绑定到 Context 中
			// try 自动关闭 Scope，确保线程执行结束后，Context 能够自动从当前线程解绑
			try (Scope ignored = context.makeCurrent()) {
				task.run();
			}
			finally {
				// 使用 Micrometer 库记录一个指标（Metric），用于表示自定义线程池中执行的任务数量
				log.info("自定义线程池中执行的任务数量:{}", meterRegistry.counter("custom.thread-pool.tasks-executed").count());
				meterRegistry.counter("custom.thread-pool.tasks-executed").increment();
			}
		};
	}

}
