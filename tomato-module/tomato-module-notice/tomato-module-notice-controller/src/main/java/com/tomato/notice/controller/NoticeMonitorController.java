package com.tomato.notice.controller;

import com.tomato.common.concurrent.ThreadPoolConverter;
import com.tomato.common.domain.resp.Resp;
import com.tomato.common.domain.resp.ThreadPoolStatsResp;
import jakarta.annotation.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通知监控 {@link reactor.core.scheduler.BoundedElasticScheduler}
 *
 * @author lizhifu
 * @since 2023/5/15
 */
@RestController
public class NoticeMonitorController {

	@Resource
	private TaskExecutor asyncTaskExecutor;

	/**
	 * 线程池统计信息
	 * @return Resp
	 */
	@RequestMapping("/notice/threadPoolStats")
	public Resp<ThreadPoolStatsResp> getThreadPoolStats() {
		// Scheduler scheduler = Schedulers.boundedElastic();
		ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) asyncTaskExecutor;
		ThreadPoolStatsResp convert = ThreadPoolConverter.convert(executor.getThreadPoolExecutor());
		return Resp.of(convert);
	}

}
