package com.tomato.dynamic.thread.support;

import com.tomato.dynamic.thread.support.runnable.DynamicRunnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;

import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 扩展线程池参数 {@link org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor}
 * 执行任务前方法：beforeExecute(wt, task); 执行任务：task.run(); 执行任务后方法：afterExecute(task, thrown);
 *
 * @author lizhifu
 * @date 2022/12/6
 */
@Slf4j
public abstract class ExtendThreadPoolExecutor extends ThreadPoolExecutor implements DisposableBean {

	public ExtendThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
	}

	public ExtendThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue, threadFactory);
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

	/**
	 * 是否等待任务完成： 不中断正在运行的任务并执行队列中的所有任务。
	 */
	protected boolean waitForTasksToCompleteOnShutdown = false;

	/**
	 * The maximum number of seconds that this executor is supposed to block on shutdown
	 * in order to wait for remaining tasks to complete their execution before the rest of
	 * the container continues to shut down.
	 */
	protected int awaitTerminationSeconds = 0;

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
				log.error("{} execute queue timeout waitTime: {}ms", this.getThreadPoolName(), waitTime);
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

	/**
	 * 当 spring 容器销毁时，关闭线程实例
	 */
	@Override
	public void destroy() {
		internalShutdown();
	}

	/**
	 * ExecutorService 关闭
	 * @see java.util.concurrent.ExecutorService#shutdown()
	 * @see java.util.concurrent.ExecutorService#shutdownNow()
	 */
	public void internalShutdown() {
		log.info("Shutting down ExecutorService, poolName: {}", threadPoolName);
		if (this.waitForTasksToCompleteOnShutdown) {
			// ThreadPoolExecutor的运行状态：SHUTDOWN
			// 关闭状态，不再接受新提交的任务,新提交的任务会执行拒绝策略，但却可以继续处理阻塞队列中已保存的任务。
			this.shutdown();
		}
		else {
			// ThreadPoolExecutor的运行状态：STOP
			// 不能接受新任务，也不处理队列中的任务，会中断正在处理任务的线程。
			// 返回未执行的任务
			for (Runnable remainingTask : this.shutdownNow()) {
				cancelRemainingTask(remainingTask);
			}
		}
		// 线程的停止无论是 shutdown() 还是 shutdownNow() 都无法保证线程池能够停止下来
		// 指定一个最大等待时间，则能够保证线程池最终一定可以被停止下来。
		awaitTerminationIfNecessary();
	}

	/**
	 * 取消从未执行的任务 = {@link ExecutorService#shutdownNow()}.
	 * @param task the task to cancel (typically a {@link RunnableFuture})
	 * @since 5.0.5
	 * @see #shutdown()
	 * @see RunnableFuture#cancel(boolean)
	 */
	protected void cancelRemainingTask(Runnable task) {
		if (task instanceof Future) {
			((Future<?>) task).cancel(true);
		}
	}

	/**
	 * Wait for the executor to terminate, according to the value of the
	 * {@link #setAwaitTerminationSeconds "awaitTerminationSeconds"} property. 在
	 * awaitTerminationSeconds 时间内等待 ThreadPoolExecutor 状态变成 TERMINATE（终止）
	 */
	private void awaitTerminationIfNecessary() {
		if (this.awaitTerminationSeconds <= 0) {
			return;
		}
		try {
			if (!awaitTermination(this.awaitTerminationSeconds, TimeUnit.SECONDS)) {
				log.warn("Timed out while waiting for executor {} to terminate", threadPoolName);
			}
		}
		catch (InterruptedException ex) {
			log.warn("Interrupted while waiting for executor {} to terminate", threadPoolName);
			// 捕获 InterruptedException 时，线程的中断标志将被清除
			// 再次设置了中断标志，以便堆栈更高的客户端知道线程已被中断并可以做出相应的 react
			Thread.currentThread().interrupt();
		}
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
