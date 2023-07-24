package com.tomato.merchant.domain.req;

import com.tomato.merchant.domain.entity.MerchantConfig;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 商户配置 {@link MerchantConfig}
 *
 * @author lizhifu
 * @since 2023/7/24
 */
@Data
public class MerchantConfigReq {
    /**
     * 商户编号
     */
    @NotBlank(message = "商户编号不能为空")
    private String merchantNo;
}
