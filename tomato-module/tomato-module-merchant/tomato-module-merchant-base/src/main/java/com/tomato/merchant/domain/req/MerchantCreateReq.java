package com.tomato.merchant.domain.req;

import com.tomato.common.util.RegexPool;
import com.tomato.module.common.enums.MerchantType;
import com.tomato.validator.annotation.CheckEnum;
import com.tomato.validator.annotation.Mobile;
import jakarta.validation.constraints.*;
import lombok.Data;


/**
 * 商户创建
 *
 * @author lizhifu
 * @since  2022/12/13
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
    @Mobile(message = "手机号格式不正确")
    private String phone;
    /**
     * 邮箱
     */
    @Pattern(regexp = RegexPool.EMAIL,message = "邮箱格式不正确")
    @NotBlank
    private String email;

    /**
     * 商户类型
     */
    @NotBlank(message = "商户类型不能为空")
    @CheckEnum(value = MerchantType.class,message = "商户类型不正确",required = true)
    private String merchantType;
}
