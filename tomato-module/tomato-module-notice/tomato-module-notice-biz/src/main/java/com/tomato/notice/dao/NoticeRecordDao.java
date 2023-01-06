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
     * @param id 通知记录id
     * @param state 状态
     * @param noticeResult 通知结果
     * @return 条数
     */
    Integer updateNoticeResult(@Param("id") Long id,
                               @Param("state") Byte state,
                               @Param("noticeResult") String noticeResult);

    /**
     * 根据 ID 查询
     * @param id id
     * @return
     */
    NoticeRecordEntity selectById(@Param("id") Long id);
}
