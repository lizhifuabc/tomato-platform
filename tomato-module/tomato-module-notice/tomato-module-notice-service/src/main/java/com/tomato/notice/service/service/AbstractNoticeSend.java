package com.tomato.notice.service.service;

import com.tomato.notice.entity.NoticeRecordEntity;
import com.tomato.notice.entity.NoticeRecordHistoryEntity;
import com.tomato.notice.service.manager.NoticeRecordManager;

/**
 * AbstractNoticeSend 发送通知
 *
 * @author lizhifu
 * @since 2024/1/12
 */
public abstract class AbstractNoticeSend {

	private final NoticeRecordManager noticeRecordManager;

    protected AbstractNoticeSend(NoticeRecordManager noticeRecordManager) {
        this.noticeRecordManager = noticeRecordManager;
    }

    public void send(Long id){
		NoticeRecordEntity noticeRecordEntity = noticeRecordManager.selectById(id);
		// 创建通知历史记录
		NoticeRecordHistoryEntity noticeHis = noticeRecordManager.createNoticeHis(noticeRecordEntity);
		// 发送通知
		send(noticeRecordEntity,noticeHis);
	}

	/**
	 * 发送通知
	 *
	 * @param noticeRecordEntity 通知请求
	 * @param noticeHis 通知历史记录
	 */
	public abstract void send(NoticeRecordEntity noticeRecordEntity,NoticeRecordHistoryEntity noticeHis);
}
