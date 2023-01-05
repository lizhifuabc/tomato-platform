package com.tomato.notice;

import com.tomato.notice.domain.req.NoticeCreateReq;
import com.tomato.notice.service.NoticeRecordService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

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

    public static void main(String[] args) {

    }
    @Test
    public void test(){
        Map<String,String> map = new HashMap<>();
        map.put("test","test");
        map.put("test2","test2");
        NoticeCreateReq noticeCreateReq = new NoticeCreateReq();
        noticeCreateReq.setNoticeUrl("http://baidu.com");
        noticeCreateReq.setAppNo("SAAS");
        noticeCreateReq.setMerchantNo("100000000");
        noticeCreateReq.setOrderNo(UUID.randomUUID().toString());
        noticeCreateReq.setMerchantOrderNo(UUID.randomUUID().toString());
        noticeCreateReq.setNoticeParam(map);
        noticeRecordService.createNotice(noticeCreateReq);
    }
}
