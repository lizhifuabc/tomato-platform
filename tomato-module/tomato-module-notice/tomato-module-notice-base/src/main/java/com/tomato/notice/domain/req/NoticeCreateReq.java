package com.tomato.notice.domain.req;

import com.tomato.notice.domain.entity.NoticeRecordEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Map;

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
    @NotBlank
    private String orderNo;

    /**
     * 商户编号
     */
    @NotBlank
    private String merchantNo;

    /**
     * 商户订单号
     */
    @NotBlank
    private String merchantOrderNo;

    /**
     * 系统编号(对接多个系统使用)
     */
    @NotBlank
    private String appNo;

    /**
     * 通知地址
     */
    @NotBlank
    private String noticeUrl;
    /**
     * 通知参数
     */
    @NotEmpty
    private Map<String,String> noticeParam;

}
