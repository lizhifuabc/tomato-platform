package com.tomato.dynamic.thread.support.runnable;

/**
 * 自定义 Runnable：线程的执行体 记录任务创建(提交)时间，任务开始执行时间
 *
 * @author lizhifu
 * @date 2022/12/5
 */
public class DynamicRunnable implements Runnable {

	/**
	 * runnable
	 */
	private final Runnable runnable;

	/**
	 * 任务创建(提交)时间
	 */
	private final Long submitTime;

	/**
	 * 任务开始执行时间
	 */
	private Long startExeTime;

	public DynamicRunnable(Runnable runnable) {
		this.runnable = runnable;
		submitTime = System.currentTimeMillis();
	}

	@Override
	public void run() {
		runnable.run();
	}

	public Long getSubmitTime() {
		return submitTime;
	}

	public void setStartExeTime(Long startExeTime) {
		this.startExeTime = startExeTime;
	}

	public Long getStartExeTime() {
		return startExeTime;
	}

}
