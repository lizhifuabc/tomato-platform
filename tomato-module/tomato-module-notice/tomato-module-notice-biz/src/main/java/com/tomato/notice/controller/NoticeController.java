package com.tomato.notice.controller;

import com.tomato.domain.resp.Resp;
import com.tomato.notice.domain.req.NoticeCreateReq;
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
    @PostMapping("/notice/create")
    public Resp createNotice(@Validated @RequestBody NoticeCreateReq noticeCreateReq){
        return Resp.buildSuccess();
    }
}
