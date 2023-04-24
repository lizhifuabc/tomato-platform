package com.tomato.notice.mapper;

import com.tomato.notice.domain.entity.NoticeRecordHistoryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 通知记录历史
 *
 * @author lizhifu
 * @since 2023/4/24
 */
@Mapper
public interface NoticeRecordHistoryMapper {
    /**
     * 插入
     * @param noticeRecordHistoryEntity 通知记录历史
     */
    void insert(NoticeRecordHistoryEntity noticeRecordHistoryEntity);
}
