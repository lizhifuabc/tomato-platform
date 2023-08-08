package com.tomato.channel.vo.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 渠道请求参数
 *
 * @author lizhifu
 * @since 2023/8/5
 */
@Data
public class ChannelReq {
    /**
     * 支付方式
     */
    @NotNull(message = "支付方式不能为空")
    private Integer payType;

    /**
     * 商户编号
     */
    @NotBlank(message = "商户编号不能为空")
    private String merchantNo;
}
