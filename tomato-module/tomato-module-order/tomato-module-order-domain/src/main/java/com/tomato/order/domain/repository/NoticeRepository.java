package com.tomato.order.domain.repository;

import com.tomato.order.domain.domain.entity.NoticeEntity;

/**
 * 通知系统
 *
 * @author lizhifu
 * @since 2023/8/12
 */
public interface NoticeRepository {

	/**
	 * 通知
	 * @param noticeEntity 通知请求
	 */
	void createNotice(NoticeEntity noticeEntity);

}
