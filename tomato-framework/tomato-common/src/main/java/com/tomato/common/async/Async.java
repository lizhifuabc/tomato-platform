package com.tomato.common.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步任务执行
 *
 * @author lizhifu
 * @since 2023/6/1
 */
public class Async {

	/**
	 * 公共线程池
	 */
	private static final ThreadPoolExecutor COMMON_POOL = (ThreadPoolExecutor) Executors.newCachedThreadPool();

	public static boolean exe() {
		CompletableFuture.runAsync(() -> {
			System.out.println("异步任务");
		}, COMMON_POOL);
		return true;
	}

}
