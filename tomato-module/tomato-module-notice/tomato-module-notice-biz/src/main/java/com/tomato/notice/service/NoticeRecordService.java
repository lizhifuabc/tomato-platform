package com.tomato.notice.service;

import com.tomato.notice.domain.entity.NoticeRecordEntity;
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
    private final NoticeSendService noticeSendService;
    public NoticeRecordService(NoticeRecordManager noticeRecordManager, NoticeSendService noticeSendService) {
        this.noticeRecordManager = noticeRecordManager;
        this.noticeSendService = noticeSendService;
    }
    public void createNotice(NoticeCreateReq noticeCreateReq){
        // 创建通知记录，如果已经存在则不创建；存在则发送通知
        NoticeRecordEntity noticeRecordEntity = noticeRecordManager.selectByMerchant(noticeCreateReq.getMerchantNo(),noticeCreateReq.getMerchantOrderNo())
                .orElseGet(() -> noticeRecordManager.createNotice(noticeCreateReq));
        noticeSendService.send(noticeRecordEntity.getId());
    }
    public void createNoticeAsync(NoticeCreateReq noticeCreateReq){
        // 创建通知记录，如果已经存在则不创建；存在则发送通知
        NoticeRecordEntity noticeRecordEntity = noticeRecordManager.selectByMerchant(noticeCreateReq.getMerchantNo(),noticeCreateReq.getMerchantOrderNo())
                .orElseGet(() -> noticeRecordManager.createNotice(noticeCreateReq));
        noticeSendService.sendAsync(noticeRecordEntity.getId());
    }
}
