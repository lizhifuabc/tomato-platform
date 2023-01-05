package com.tomato.notice.domain.entity;

import com.tomato.domain.entity.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 商户通知记录表
 *
 * @author lizhifu
 * @since 2023/1/4
 */
@Data
public class NoticeRecordEntity extends BaseEntity {
    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 商户编号
     */
    private String merchantNo;

    /**
     * 商户订单号
     */
    private String merchantOrderNo;

    /**
     * 系统编号(对接多个系统使用)
     */
    private String appNo;

    /**
     * 通知地址
     */
    private String noticeUrl;

    /**
     * 通知响应结果
     */
    private String resResult;

    /**
     * 通知次数
     */
    private Integer noticeCount;

    /**
     * 最大通知次数， 默认6次
     */
    private Integer noticeCountLimit;

    /**
     * 通知状态，1-通知中，2-通知成功，3-通知失败
     */
    private Byte state;

    /**
     * 最后一次通知时间
     */
    private LocalDateTime lastNoticeTime;
}
