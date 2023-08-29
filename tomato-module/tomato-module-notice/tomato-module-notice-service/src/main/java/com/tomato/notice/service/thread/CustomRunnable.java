package com.tomato.notice.service.thread;

import com.tomato.notice.common.constant.CustomThreadConstant;
import org.slf4j.MDC;

/**
 * 自定义 runnable
 *
 * @author lizhifu
 * @since 2023/5/15
 */
public class CustomRunnable implements Runnable {

	private final Runnable runnable;

	private final String traceId;

	public CustomRunnable(Runnable runnable) {
		this.runnable = runnable;
		this.traceId = MDC.get(CustomThreadConstant.TRACE_ID);
	}

	@Override
	public void run() {
		runnable.run();
	}

	public String getTraceId() {
		return traceId;
	}

}
