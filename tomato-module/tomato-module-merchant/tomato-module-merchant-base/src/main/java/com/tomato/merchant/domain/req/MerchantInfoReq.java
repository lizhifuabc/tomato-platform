package com.tomato.merchant.domain.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 商户信息请求
 *
 * @author lizhifu
 * @date 2022/11/25
 */
@Data
public class MerchantInfoReq {
    /**
     * 商户号
     */
    @NotBlank(message = "商户号不能为空")
    private String merchantNo;
    /**
     * 支付方式
     */
    @NotNull(message = "支付方式不能为空")
    private Byte payType;
}
