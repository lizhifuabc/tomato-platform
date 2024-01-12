package com.tomato.notice.service.service;

import com.tomato.notice.common.constant.NoticeRecordState;
import com.tomato.notice.entity.NoticeRecordEntity;
import com.tomato.notice.entity.NoticeRecordHistoryEntity;
import com.tomato.notice.service.manager.NoticeRecordManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 同步发送通知
 * <a href="https://docs.spring.io/spring-framework/reference/integration/rest-clients.html">rest-clients</a>
 *
 * @author lizhifu
 * @since 2024/1/12
 */
@Service
public class NoticeSyncSendService {
	private final RestTemplate restTemplate;

	private final NoticeRecordManager noticeRecordManager;

	private final NoticeResultService noticeResultService;

    public NoticeSyncSendService(RestTemplate restTemplate, NoticeRecordManager noticeRecordManager, NoticeResultService noticeResultService) {
        this.restTemplate = restTemplate;
        this.noticeRecordManager = noticeRecordManager;
        this.noticeResultService = noticeResultService;
    }

    public void sendSync(Long id) {
		NoticeRecordEntity noticeRecordEntity = noticeRecordManager.selectById(id);
		// 创建通知历史记录
		NoticeRecordHistoryEntity noticeHis = noticeRecordManager.createNoticeHis(noticeRecordEntity);
		// 请求头信息
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));
		// 接收成功：HTTP应答状态码需返回200或204，无需返回应答报文。
		// 接收失败：HTTP应答状态码需返回5XX或4XX，同时需返回应答报文，格式如下：
		// {
		// "code": "FAIL",
		// "message": "失败"
		// }
		HttpEntity<String> entity = new HttpEntity<>(noticeRecordEntity.getNoticeParam(), headers);
		boolean success;
		String body;
		try {
			ResponseEntity<String> responseEntity = restTemplate.postForEntity(noticeRecordEntity.getNoticeUrl(),
					entity, String.class);
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
