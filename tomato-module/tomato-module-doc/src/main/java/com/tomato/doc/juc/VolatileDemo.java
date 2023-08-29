package com.tomato.doc.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Volatile demo
 *
 * @author lizhifu
 * @date 2022/12/8
 */
public class VolatileDemo {

	public static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 4, 60, TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(1000), new ThreadPoolExecutor.DiscardOldestPolicy());

	private static volatile int i = 0;

	public static void add() {
		i++;
	}

	@lombok.SneakyThrows
	public static void main(String[] args) {
		Runnable runnable = () -> {
			add();
		};
		// 添加 1000 个任务
		for (int i = 0; i < 1000; i++) {
			poolExecutor.execute(runnable);
		}
		// 睡一会
		Thread.sleep(3000);

		int queueSize = poolExecutor.getQueue().size();
		System.out.println("当前排队任务数：" + queueSize);

		int activeCount = poolExecutor.getActiveCount();
		System.out.println("当前活动线程数：" + activeCount);

		long completedTaskCount = poolExecutor.getCompletedTaskCount();
		System.out.println("执行完成任务数：" + completedTaskCount);

		long taskCount = poolExecutor.getTaskCount();
		System.out.println("总任务数量：" + taskCount);

		System.out.println("i值为：" + i);
	}

}
