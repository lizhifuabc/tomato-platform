package com.tomato.dynamic.thread.notify;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池通知
 *
 * @author lizhifu
 * @date 2022/12/6
 */
public interface ThreadPoolNotify {

	/**
	 * Async send execute time-out alarm.
	 * @param threadPoolId thread-pool id
	 * @param executeTime execute time
	 * @param executeTimeOut execute time-out
	 * @param threadPoolExecutor thread-pool executor
	 */
	void asyncSendExecuteTimeOutAlarm(String threadPoolId, long executeTime, long executeTimeOut,
			ThreadPoolExecutor threadPoolExecutor);

}
