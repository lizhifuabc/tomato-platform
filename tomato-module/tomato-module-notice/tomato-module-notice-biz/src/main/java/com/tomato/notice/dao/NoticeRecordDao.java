package com.tomato.notice.dao;

import com.tomato.notice.domain.entity.NoticeRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 商户通知记录 {@link com.tomato.notice.domain.entity.NoticeRecordEntity}
 *
 * @author lizhifu
 * @since 2023/1/4
 */
@Mapper
public interface NoticeRecordDao {
    /**
     * 插入
     * @param noticeRecordEntity
     */
    void insert(NoticeRecordEntity noticeRecordEntity);
    /**
     * 更新通知结果
     * @param notifyId 通知记录编号
     * @param state 状态
     * @param resResult 通知结果
     * @return 条数
     */
    Integer updateNotifyResult(@Param("notifyId") Long notifyId, @Param("state") Byte state, @Param("resResult") String resResult);

    /**
     * 更改为通知中 & 增加允许重发通知次数
     * @param notifyId 通知记录编号
     * @return
     */
    Integer updateIngAndAddNotifyCountLimit(@Param("notifyId") Long notifyId);
}
