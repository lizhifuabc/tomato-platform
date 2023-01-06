package com.tomato.notice.controller;

import com.tomato.domain.resp.Resp;
import com.tomato.notice.domain.req.NoticeCreateReq;
import com.tomato.notice.service.NoticeRecordService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通知记录
 *
 * @author lizhifu
 * @since 2023/1/5
 */
@RestController
public class NoticeController {
    private final NoticeRecordService noticeRecordService;

    public NoticeController(NoticeRecordService noticeRecordService) {
        this.noticeRecordService = noticeRecordService;
    }

    @PostMapping("/notice/create")
    public Resp createNotice(@Validated @RequestBody NoticeCreateReq noticeCreateReq){
        noticeRecordService.createNotice(noticeCreateReq);
        return Resp.buildSuccess();
    }
}
