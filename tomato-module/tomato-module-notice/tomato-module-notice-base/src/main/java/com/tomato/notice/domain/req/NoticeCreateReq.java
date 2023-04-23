package com.tomato.notice.domain.req;

import com.tomato.notice.domain.entity.NoticeRecordEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Map;

/**
 * 通知收单 {@link NoticeRecordEntity}
 *
 * @author lizhifu
 * @since 2023/1/5
 */
@Data
@Tag(name = "通知收单", description = "通知收单")
public class NoticeCreateReq {
    /**
     * 订单号
     */
    @NotBlank
    @Schema(description = "订单号")
    private String orderNo;

    /**
     * 商户编号
     */
    @NotBlank
    @Schema(description = "商户编号")
    private String merchantNo;

    /**
     * 商户订单号
     */
    @NotBlank
    @Schema(description = "商户订单号")
    private String merchantOrderNo;

    /**
     * 系统编号(对接多个系统使用)
     */
    @NotBlank
    @Schema(description = "系统编号")
    private String appNo;

    /**
     * 通知地址
     */
    @NotBlank
    @Schema(description = "通知地址")
    private String noticeUrl;
    /**
     * 通知参数
     */
    @NotEmpty
    @Schema(description = "通知参数")
    private Map<String,String> noticeParam;

}
