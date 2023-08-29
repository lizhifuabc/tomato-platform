package com.tomato.notice.service.thread;

import com.tomato.notice.common.constant.CustomThreadConstant;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.concurrent.*;

/**
 * 自定义线程池
 *
 * @author lizhifu
 * @since 2023/5/15
 */
@Slf4j
public class CustomThreadPoolExecutor extends ThreadPoolExecutor {

	/**
	 * execute() 方法执行任务前调用
	 * @param t 将运行任务｛@code r｝的线程
	 * @param r 将要执行的任务
	 */
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
	}

	/**
	 * execute() 方法执行任务后调用
	 * @param r 已完成的可运行程序
	 * @param t 导致终止的异常，或者如果执行正常完成，则为null
	 */
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		MDC.remove(CustomThreadConstant.TRACE_ID);
	}

	/**
	 * 将任务添加到工作者线程中执行
	 * @param command 要执行的任务
	 */
	@Override
	public void execute(Runnable command) {
		super.execute(command);
	}

	public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
	}

	public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
	}

	public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
	}

}
