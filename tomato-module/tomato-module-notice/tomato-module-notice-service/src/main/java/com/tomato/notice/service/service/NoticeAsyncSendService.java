package com.tomato.notice.service.service;

import com.tomato.notice.entity.NoticeRecordEntity;
import com.tomato.notice.entity.NoticeRecordHistoryEntity;
import com.tomato.notice.service.manager.NoticeRecordManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Objects;

/**
 * 发送http请求
 *
 * @author lizhifu
 * @since 2023/1/6
 */
@Slf4j
@Service
public class NoticeAsyncSendService extends AbstractNoticeSend {

	private final NoticeResultService noticeResultService;

	private final WebClient webClient;

	public NoticeAsyncSendService(NoticeRecordManager noticeRecordManager,
								  NoticeResultService noticeResultService, WebClient webClient) {
        super(noticeRecordManager);
		this.noticeResultService = noticeResultService;
		this.webClient = webClient;
	}

	@Override
	public void send(NoticeRecordEntity noticeRecordEntity, NoticeRecordHistoryEntity noticeHis) {
		// webClient 发送post请求，并对错误进行处理
		// TODO webClient 重试方式进行优化??rabbitmq延迟队列
		webClient.post()
			.uri(noticeRecordEntity.getNoticeUrl())
			.body(BodyInserters.fromValue(noticeRecordEntity.getNoticeParam()))
			// 获取响应体
			.retrieve()
			// 响应数据类型转换
			.bodyToMono(String.class)
			// 重试3次
			.retryWhen(Retry.backoff(6, Duration.ofSeconds(30)).doAfterRetry(retrySignal -> {
				log.info("重试次数：{}，异常信息：{}", retrySignal.totalRetriesInARow(), retrySignal.failure().getMessage());
				noticeResultService.fail(retrySignal.failure().getMessage(), noticeHis);
			})
				.filter(throwable -> throwable instanceof Exception)
				.onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> new RuntimeException("重试3次失败")))
			// 异步处理:将后续的操作切换到一个由 boundedElastic 线程池维护的线程中执行
			// TODO boundedElastic 线程池使用的是有界的队列，可以防止线程数无限增长，从而避免资源耗尽的风险。
			// 当任务数量超过队列容量时，会触发线程池的饱和策略，比如说抛出异常或者丢弃任务。
			.publishOn(Schedulers.boundedElastic())
			.doOnError(throwable -> {
				String msg = Objects.isNull(throwable.getMessage()) ? throwable.getCause().toString()
						: throwable.getMessage();
				noticeResultService.fail(msg, noticeHis);
			})
			.subscribe(result -> {
				noticeResultService.success(result, noticeHis);
			});
	}

}