package com.tomato.dynamic.thread.thread;

import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 复制于 {@link org.springframework.util.CustomizableThreadCreator}
 * {@link com.tomato.dynamic.thread.thread.CustomThreadFactory}. 用于创建新的｛@link
 * Thread｝实例的简单可自定义帮助程序类。 提供各种bean属性：线程名称前缀、线程优先级等。
 *
 * @author lizhifu
 */
public class CustomThreadCreator implements Serializable {

	/**
	 * 线程名称前缀
	 */
	private String threadNamePrefix;

	/**
	 * 线程优先级
	 */
	private int threadPriority = Thread.NORM_PRIORITY;

	private boolean daemon = false;

	@Nullable
	private ThreadGroup threadGroup;

	private final AtomicInteger threadCount = new AtomicInteger();

	/**
	 * Create a new CustomizableThreadCreator with default thread name prefix.
	 */
	public CustomThreadCreator() {
		this.threadNamePrefix = getDefaultThreadNamePrefix();
	}

	/**
	 * Create a new CustomizableThreadCreator with the given thread name prefix.
	 * @param threadNamePrefix the prefix to use for the names of newly created threads
	 */
	public CustomThreadCreator(@Nullable String threadNamePrefix) {
		this.threadNamePrefix = (threadNamePrefix != null ? threadNamePrefix : getDefaultThreadNamePrefix());
	}

	/**
	 * Specify the prefix to use for the names of newly created threads. Default is
	 * "SimpleAsyncTaskExecutor-".
	 */
	public void setThreadNamePrefix(@Nullable String threadNamePrefix) {
		this.threadNamePrefix = (threadNamePrefix != null ? threadNamePrefix : getDefaultThreadNamePrefix());
	}

	/**
	 * Return the thread name prefix to use for the names of newly created threads.
	 */
	public String getThreadNamePrefix() {
		return this.threadNamePrefix;
	}

	/**
	 * Set the priority of the threads that this factory creates. Default is 5.
	 * @see Thread#NORM_PRIORITY
	 */
	public void setThreadPriority(int threadPriority) {
		this.threadPriority = threadPriority;
	}

	/**
	 * Return the priority of the threads that this factory creates.
	 */
	public int getThreadPriority() {
		return this.threadPriority;
	}

	/**
	 * Set whether this factory is supposed to create daemon threads, just executing as
	 * long as the application itself is running.
	 * <p>
	 * Default is "false": Concrete factories usually support explicit cancelling. Hence,
	 * if the application shuts down, Runnables will by default finish their execution.
	 * <p>
	 * Specify "true" for eager shutdown of threads which still actively execute a
	 * {@link Runnable} at the time that the application itself shuts down.
	 * @see Thread#setDaemon
	 */
	public void setDaemon(boolean daemon) {
		this.daemon = daemon;
	}

	/**
	 * Return whether this factory should create daemon threads.
	 */
	public boolean isDaemon() {
		return this.daemon;
	}

	/**
	 * Specify the name of the thread group that threads should be created in.
	 * @see #setThreadGroup
	 */
	public void setThreadGroupName(String name) {
		this.threadGroup = new ThreadGroup(name);
	}

	/**
	 * Specify the thread group that threads should be created in.
	 * @see #setThreadGroupName
	 */
	public void setThreadGroup(@Nullable ThreadGroup threadGroup) {
		this.threadGroup = threadGroup;
	}

	/**
	 * Return the thread group that threads should be created in (or {@code null} for the
	 * default group).
	 */
	@Nullable
	public ThreadGroup getThreadGroup() {
		return this.threadGroup;
	}

	/**
	 * 用于创建新模板的模板方法 {@link Thread}.
	 * <p>
	 * 默认实现为给定的的线程 {@link Runnable}, applying an appropriate thread name.
	 * @param runnable the Runnable to execute
	 * @see #nextThreadName()
	 */
	public Thread createThread(Runnable runnable) {
		Thread thread = new Thread(getThreadGroup(), runnable, nextThreadName());
		thread.setPriority(getThreadPriority());
		thread.setDaemon(isDaemon());
		return thread;
	}

	/**
	 * Return the thread name to use for a newly created {@link Thread}.
	 * <p>
	 * The default implementation returns the specified thread name prefix with an
	 * increasing thread count appended: e.g. "SimpleAsyncTaskExecutor-0".
	 * @see #getThreadNamePrefix()
	 */
	protected String nextThreadName() {
		return getThreadNamePrefix() + this.threadCount.incrementAndGet();
	}

	/**
	 * Build the default thread name prefix for this factory.
	 * @return the default thread name prefix (never {@code null})
	 */
	protected String getDefaultThreadNamePrefix() {
		return ClassUtils.getShortName(getClass()) + "-";
	}

}
