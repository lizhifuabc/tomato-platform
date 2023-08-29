package com.tomato.dynamic.thread.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 动态线程池属性配置
 *
 * @author lizhifu
 * @date 2022/12/4
 */
@ConfigurationProperties("tomato.dynamic.task.execution")
public class DynamicThreadPoolProperties {

	/**
	 * 线程池参数
	 */
	public static class Pool {

		/**
		 * 队列容量，无界队列时无效。
		 */
		private int queueCapacity = Integer.MAX_VALUE;

		/**
		 * 队列类型，默认 LinkedBlockingQueue
		 */
		private BlockingQueue workQueue = new LinkedBlockingQueue<>(queueCapacity);

		/**
		 * 核心线程数，默认 8 如果workerCount < corePoolSize，则创建并启动一个线程来执行新提交的任务。
		 */
		private int corePoolSize = 8;

		/**
		 * 最大线程数 默认为 Integer 最大值 如果是无界队列，则此参数无效。 如果是有界队列，队列满了之后，则创建并启动一个线程来执行新提交的任务。
		 */
		private int maxPoolSize = Integer.MAX_VALUE;

		/**
		 * 是否允许核心线程超时，线程池中corePoolSize线程空闲时间达到keepAliveTime也将关闭， 这样就可以线程池动态变化。
		 */
		private boolean allowCoreThreadTimeout = true;

		/**
		 * 当线程池中的线程数大于corePoolSize时keepAliveTime才会起作用 空闲线程存活时间
		 */
		private Duration keepAlive = Duration.ofSeconds(60);

	}

}
