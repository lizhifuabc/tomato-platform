package com.tomato.notice.service.service;

import com.tomato.notice.common.constant.NoticeRecordState;
import com.tomato.notice.dto.NoticeDelayBO;
import com.tomato.notice.entity.NoticeRecordEntity;
import com.tomato.notice.entity.NoticeRecordHistoryEntity;
import com.tomato.notice.service.manager.NoticeRecordManager;
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

    public void failMQ(NoticeRecordEntity noticeRecordEntity, String msg,NoticeRecordHistoryEntity noticeRecordHistoryEntity){
        noticeRecordHistoryEntity.setState(NoticeRecordState.STATE_FAIL);
        noticeRecordHistoryEntity.setNoticeResult(msg);
        noticeRecordManager.noticeResult(noticeRecordHistoryEntity);
        // 通知次数 >= 最大通知次数时
        if(noticeRecordEntity.getNoticeCount() >= noticeRecordEntity.getNoticeCountLimit()){
            return;
        }
        NoticeDelayBO noticeDelayBO = new NoticeDelayBO();
        noticeDelayBO.setNoticeCount(noticeRecordEntity.getNoticeCount());
        noticeDelayBO.setId(noticeRecordEntity.getId());
        noticeRabbitService.delayNotice(noticeDelayBO);
    }
    public void fail(String msg,NoticeRecordHistoryEntity noticeRecordHistoryEntity){
        noticeRecordHistoryEntity.setState(NoticeRecordState.STATE_FAIL);
        noticeRecordHistoryEntity.setNoticeResult(msg);
        noticeRecordManager.noticeResult(noticeRecordHistoryEntity);
    }
    /**
     * 通知成功
     * @param noticeRecordHistoryEntity 通知记录历史
     * @param body 通知结果
     */
    public void success(String body, NoticeRecordHistoryEntity noticeRecordHistoryEntity){
        noticeRecordHistoryEntity.setState(NoticeRecordState.STATE_SUCCESS);
        noticeRecordHistoryEntity.setNoticeResult(body);
        noticeRecordManager.noticeResult(noticeRecordHistoryEntity);
    }
}
