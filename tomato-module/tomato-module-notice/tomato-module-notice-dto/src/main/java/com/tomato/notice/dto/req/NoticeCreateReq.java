package com.tomato.notice.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Map;

/**
 * 通知收单
 *
 * @author lizhifu
 * @since 2023/1/5
 */
@Data
@Tag(name = "通知收单", description = "通知收单")
public class NoticeCreateReq {
    /**
     * http请求方式
     */
    @Schema(description = "http请求方式")
    @NotBlank(message = "http请求方式不能为空")
    private String httpMethod;
    /**
     * 规则编码
     */
    @NotBlank(message = "规则编码不能为空")
    @Schema(description = "规则编码")
    private String ruleCode;
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
    @Length(max = 16)
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
