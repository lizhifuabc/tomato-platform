package com.tomato.notice.service.service;

import com.tomato.notice.common.constant.NoticeRecordState;
import com.tomato.notice.entity.NoticeRecordEntity;
import com.tomato.notice.entity.NoticeRecordHistoryEntity;
import com.tomato.notice.service.manager.NoticeRecordManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * 同步发送通知
 * <a href="https://docs.spring.io/spring-framework/reference/integration/rest-clients.html">rest-clients</a>
 *
 * @author lizhifu
 * @since 2024/1/12
 */
@Service
@Slf4j
public class NoticeSyncSendService extends AbstractNoticeSend {
	private final RestClient restClient;

	private final NoticeResultService noticeResultService;

    public NoticeSyncSendService(RestClient restClient, NoticeRecordManager noticeRecordManager, NoticeResultService noticeResultService) {
        super(noticeRecordManager);
        this.restClient = restClient;
        this.noticeResultService = noticeResultService;
    }

	@Override
	public void send(NoticeRecordEntity noticeRecordEntity, NoticeRecordHistoryEntity noticeHis) {
		ResponseEntity<String> responseEntity = restClient.post()
				.uri(noticeRecordEntity.getNoticeUrl())
				.body(noticeRecordEntity.getNoticeParam())
				.retrieve()
				.onStatus(HttpStatusCode::is2xxSuccessful, (request, response) -> {
				})
				.toEntity(String.class);

		// 接收成功：HTTP应答状态码需返回200或204，无需返回应答报文。
		// 接收失败：HTTP应答状态码需返回5XX或4XX，同时需返回应答报文，格式如下：
		// {
		// "code": "FAIL",
		// "message": "失败"
		// }
		boolean success;
		String body;
		try {
			success = responseEntity.getStatusCode().is2xxSuccessful()
					&& NoticeRecordState.SUCCESS.equalsIgnoreCase(responseEntity.getBody());
			body = responseEntity.getBody();
		}
		catch (Exception e) {
			body = e.getMessage();
			success = false;
		}
		if (success) {
			noticeResultService.success(body, noticeHis);
		}
		else {
			noticeResultService.failMQ(noticeRecordEntity, body, noticeHis);
		}
	}
}
