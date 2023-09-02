package com.tomato.redis.mq;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO
 *
 * @author lizhifu
 * @since 2023/9/1
 */
public class AsyncExecutor {

	public static Executor asyncExecutor(){
		AtomicInteger index = new AtomicInteger(1);
		int processors = Runtime.getRuntime().availableProcessors();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(processors, processors, 0, TimeUnit.SECONDS,
				new LinkedBlockingDeque<>(), r -> {
			Thread thread = new Thread(r);
			thread.setName("async-stream-consumer-" + index.getAndIncrement());
			thread.setDaemon(true);
			return thread;
		});
		return executor;
	}
}
