package com.tomato.common.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * {@link Runtime#freeMemory()} 计算内存
 * @see <a href="https://github.com/apache/shenyu/blob/master/shenyu-common/src/main/java/org/apache/shenyu/common/concurrent/MemoryLimitCalculator.java">MemoryLimitCalculator</a>
 * @author lizhifu
 * @since 2023/10/11
 */
public class MemoryLimitCalculator {
	/**
	 * 当前JVM最大可用内存
	 */
	private static volatile long maxAvailable;
	/**
	 * 定时任务,每50毫秒检查一次以提高性能
	 */
	private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();

	static {
		// 加载此类时立即刷新以防止maxAvailable为0
		refresh();
		// 每50毫秒检查一次以提高性能
		SCHEDULER.scheduleWithFixedDelay(MemoryLimitCalculator::refresh, 50, 50, TimeUnit.MILLISECONDS);
		// 优雅关闭，防止内存泄漏
		Runtime.getRuntime().addShutdownHook(new Thread(SCHEDULER::shutdown));
	}

	/**
	 * 获取当前JVM最大可用内存
	 */
	private static void refresh() {
		maxAvailable = Runtime.getRuntime().freeMemory();
	}

	/**
	 * 获取当前JVM最大可用内存
	 * @return 最大可用内存
	 */
	public static long maxAvailable() {
		return maxAvailable;
	}

	public static void main(String[] args) {
		System.out.println(MemoryLimitCalculator.maxAvailable());
	}
}
