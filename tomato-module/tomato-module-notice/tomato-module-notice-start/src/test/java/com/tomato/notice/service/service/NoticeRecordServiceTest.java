package com.tomato.notice.service.service;

import com.tomato.notice.dto.req.NoticeCreateReq;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * NoticeRecordService
 *
 * @author lizhifu
 * @since 2024/1/12
 */
@SpringBootTest
public class NoticeRecordServiceTest {
	@Resource
	NoticeRecordService noticeRecordService;

	@Test
	public void test() {
		NoticeCreateReq noticeCreateReq = new NoticeCreateReq();
		noticeCreateReq.setMerchantNo("123456");
		noticeCreateReq.setMerchantOrderNo("admin");
		noticeCreateReq.setOrderNo("123456");
		noticeCreateReq.setAppNo("SAAS");
		noticeCreateReq.setNoticeUrl("https://baidu.com/");
		Map<String, String> noticeParam = Map.of("namel", "Marydonl", "agel", "18");
		noticeCreateReq.setNoticeParam(noticeParam);
		noticeRecordService.createNotice(noticeCreateReq);
	}
}
