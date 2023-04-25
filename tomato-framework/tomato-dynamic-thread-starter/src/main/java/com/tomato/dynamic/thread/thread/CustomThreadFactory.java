package com.tomato.dynamic.thread.thread;

import org.springframework.lang.NonNull;

import java.util.concurrent.ThreadFactory;

/**
 * {@link com.tomato.dynamic.thread.thread.CustomThreadCreator}.
 * {@link org.springframework.scheduling.concurrent.CustomizableThreadFactory}
 * @author lizhifu
 */
public class CustomThreadFactory extends CustomThreadCreator implements ThreadFactory {

	/**
	 * 默认线程名称前缀
	 */
	public CustomThreadFactory() {
		super();
	}

	/**
	 * 自定义线程名称前缀
	 * @param threadNamePrefix 线程名称前缀
	 */
	public CustomThreadFactory(String threadNamePrefix) {
		super(threadNamePrefix);
	}


	@Override
	public Thread newThread(@NonNull Runnable runnable) {
		return createThread(runnable);
	}

}
