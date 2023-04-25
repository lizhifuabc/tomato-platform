package com.tomato.notice.service;

import com.tomato.notice.constant.NoticeRecordState;
import com.tomato.notice.domain.bo.NoticeDelayBO;
import com.tomato.notice.domain.entity.NoticeRecordEntity;
import com.tomato.notice.manager.NoticeRecordManager;
import org.springframework.stereotype.Service;


/**
 * 通知结果
 *
 * @author lizhifu
 * @since 2023/4/25
 */
@Service
public class NoticeResultService {
    private final NoticeRecordManager noticeRecordManager;
    private final NoticeRabbitService noticeRabbitService;

    public NoticeResultService(NoticeRecordManager noticeRecordManager, NoticeRabbitService noticeRabbitService) {
        this.noticeRecordManager = noticeRecordManager;
        this.noticeRabbitService = noticeRabbitService;
    }

    public void fail(NoticeRecordEntity noticeRecordEntity,String msg){
        noticeRecordManager.noticeResult(noticeRecordEntity.getId(), NoticeRecordState.STATE_FAIL,msg);
        // 通知次数 >= 最大通知次数时
        if(noticeRecordEntity.getNoticeCount() >= noticeRecordEntity.getNoticeCountLimit()){
            return;
        }
        NoticeDelayBO noticeDelayBO = new NoticeDelayBO();
        noticeDelayBO.setNoticeCount(noticeRecordEntity.getNoticeCount());
        noticeDelayBO.setId(noticeRecordEntity.getId());
        noticeRabbitService.delayNotice(noticeDelayBO);
    }

    /**
     * 通知成功
     * @param noticeRecordEntity 通知记录
     * @param body 通知结果
     */
    public void success(NoticeRecordEntity noticeRecordEntity,String body){
        noticeRecordManager.noticeResult(noticeRecordEntity.getId(), NoticeRecordState.STATE_SUCCESS,body);
    }
}
