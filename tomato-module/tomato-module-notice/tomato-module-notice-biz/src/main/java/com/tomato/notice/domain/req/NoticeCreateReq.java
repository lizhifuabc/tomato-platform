package com.tomato.notice.domain.req;

import com.tomato.notice.domain.entity.NoticeRecordEntity;
import lombok.Data;

/**
 * 商户通知记录 {@link NoticeRecordEntity}
 *
 * @author lizhifu
 * @since 2023/1/5
 */
@Data
public class NoticeCreateReq {
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
}
