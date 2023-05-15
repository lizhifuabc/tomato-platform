package com.tomato.notice.mapper;

import com.tomato.notice.entity.NoticeRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 商户通知记录 {@link NoticeRecordEntity}
 *
 * @author lizhifu
 * @since 2023/1/4
 */
@Mapper
public interface NoticeRecordMapper {
    /**
     * 插入
     * @param noticeRecordEntity 通知记录
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
    /**
     * 根据商户号和商户订单号查询
     * @param merchantNo 商户号
     * @param merchantOrderNo 商户订单号
     * @return NoticeRecordEntity
     */
    NoticeRecordEntity selectByMerchant(@Param("merchantNo") String merchantNo,@Param("merchantOrderNo") String merchantOrderNo);
}
