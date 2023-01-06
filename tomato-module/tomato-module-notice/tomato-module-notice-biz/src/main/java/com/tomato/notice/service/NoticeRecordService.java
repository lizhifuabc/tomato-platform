package com.tomato.notice.service;

import com.tomato.notice.constant.NoticeRecordState;
import com.tomato.notice.domain.entity.NoticeRecordEntity;
import com.tomato.notice.domain.req.NoticeCreateReq;
import com.tomato.notice.manager.NoticeRecordManager;
import org.springframework.http.ResponseEntity;
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
    private final NoticeSendService noticeSendService;
    public NoticeRecordService(NoticeRecordManager noticeRecordManager, NoticeSendService noticeSendService) {
        this.noticeRecordManager = noticeRecordManager;
        this.noticeSendService = noticeSendService;
    }
    public void createNotice(NoticeCreateReq noticeCreateReq){
        NoticeRecordEntity noticeRecordEntity = noticeRecordManager.createNotice(noticeCreateReq);
        ResponseEntity<String> responseEntity = noticeSendService.send(noticeCreateReq);
        if(responseEntity.getStatusCode().is2xxSuccessful() && NoticeRecordState.SUCCESS.equalsIgnoreCase(responseEntity.getBody())){
            noticeRecordManager.noticeResult(noticeRecordEntity.getId(), NoticeRecordState.STATE_SUCCESS,responseEntity.getBody());
        }else {
            noticeRecordManager.noticeResult(noticeRecordEntity.getId(), NoticeRecordState.STATE_FAIL,responseEntity.getBody());
            // TODO 重发通知到MQ
        }
    }
}
