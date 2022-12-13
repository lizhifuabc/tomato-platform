package com.tomato.merchant.domain.req;

import com.tomato.validator.annotation.Mobile;
import com.tomato.validator.validator.VerificationPattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 商户创建
 *
 * @author lizhifu
 * @date 2022/12/13
 */
@Data
public class MerchantCreateReq {
    /**
     * 商户名称
     */
    @NotBlank(message = "商户名称不能为空")
    private String merchantName;

    /**
     * 商户简称
     */
    @NotBlank(message = "商户简称不能为空")
    private String merchantShortName;
    /**
     * 手机号
     */
    @Mobile
    private String phone;
    /**
     * 邮箱
     */
    @Pattern(regexp = VerificationPattern.EMAIL,message = "邮箱格式不正确")
    @NotBlank
    private String email;
}
