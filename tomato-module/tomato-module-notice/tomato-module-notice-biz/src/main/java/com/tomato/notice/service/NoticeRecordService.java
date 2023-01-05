package com.tomato.notice.service;

import com.tomato.notice.domain.req.NoticeCreateReq;
import com.tomato.notice.manager.NoticeRecordManager;
import org.springframework.stereotype.Service;

/**
 * 商户通知记录
 *
 * @author lizhifu
 * @since 2023/1/5
 */
@Service
public class NoticeRecordService {
    private final NoticeRecordManager noticeRecordManager;

    public NoticeRecordService(NoticeRecordManager noticeRecordManager) {
        this.noticeRecordManager = noticeRecordManager;
    }
    public void createNotice(NoticeCreateReq noticeCreateReq){
        noticeRecordManager.createNotice(noticeCreateReq);
    }
}
