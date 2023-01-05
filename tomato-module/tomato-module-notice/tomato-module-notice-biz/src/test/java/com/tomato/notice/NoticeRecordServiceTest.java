package com.tomato.notice;

import com.tomato.notice.domain.req.NoticeCreateReq;
import com.tomato.notice.service.NoticeRecordService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * NoticeRecordService
 *
 * @author lizhifu
 * @since 2023/1/5
 */
@SpringBootTest
public class NoticeRecordServiceTest {
    @Resource
    NoticeRecordService noticeRecordService;

    @Test
    public void test(){
        NoticeCreateReq noticeCreateReq = new NoticeCreateReq();
        noticeCreateReq.setNoticeUrl("http://baidu.com");
        noticeCreateReq.setAppNo("SAAS");
        noticeCreateReq.setMerchantNo("100000000");
        noticeCreateReq.setOrderNo(UUID.randomUUID().toString());
        noticeCreateReq.setMerchantOrderNo(UUID.randomUUID().toString());
        noticeRecordService.createNotice(noticeCreateReq);
    }
}
