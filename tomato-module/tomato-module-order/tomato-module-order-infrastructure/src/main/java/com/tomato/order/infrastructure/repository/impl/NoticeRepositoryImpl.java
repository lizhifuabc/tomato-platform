package com.tomato.order.infrastructure.repository.impl;

import com.tomato.notice.api.RemoteNoticeService;
import com.tomato.notice.dto.req.NoticeCreateReq;
import com.tomato.order.domain.domain.entity.NoticeEntity;
import com.tomato.order.domain.repository.NoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

/**
 * 通知系统
 *
 * @author lizhifu
 * @since 2023/8/12
 */
@Repository
@Slf4j
public class NoticeRepositoryImpl implements NoticeRepository {
    private final RemoteNoticeService remoteNoticeService;

    public NoticeRepositoryImpl(RemoteNoticeService remoteNoticeService) {
        this.remoteNoticeService = remoteNoticeService;
    }

    @Override
    public void createNotice(NoticeEntity noticeEntity) {
        NoticeCreateReq noticeCreateReq = new NoticeCreateReq();
        noticeCreateReq.setMerchantNo(noticeEntity.getMerchantNo());
        noticeCreateReq.setMerchantOrderNo(noticeEntity.getMerchantOrderNo());
        noticeCreateReq.setNoticeUrl(noticeEntity.getNoticeUrl());
        noticeCreateReq.setHttpMethod(HttpMethod.POST);
        noticeCreateReq.setAppNo("tomato-order");
        noticeCreateReq.setNoticeParam(noticeEntity.getNoticeParam());
        noticeCreateReq.setOrderNo(noticeEntity.getOrderNo());
        noticeCreateReq.setRuleCode("tomato-order");
        remoteNoticeService.createNotice(noticeCreateReq);
    }
}
