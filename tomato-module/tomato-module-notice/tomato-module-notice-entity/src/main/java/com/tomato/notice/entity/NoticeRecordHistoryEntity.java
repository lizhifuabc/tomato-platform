package com.tomato.notice.entity;

import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 通知记录历史
 *
 * @author lizhifu
 * @since 2023/4/24
 */
@Data
@Table(name = "t_notice_record_history")
public class NoticeRecordHistoryEntity {
    /**
     * 通知记录历史id
     */
    private Long id;

    /**
     * 通知记录id
     */
    private Long noticeRecordId;

    /**
     * 通知响应结果
     */
    private String noticeResult;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
