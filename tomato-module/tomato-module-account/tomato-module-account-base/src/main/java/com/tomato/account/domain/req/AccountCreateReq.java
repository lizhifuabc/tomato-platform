package com.tomato.account.domain.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 账户创建请求
 *
 * @author lizhifu
 * @date 2022/7/1
 */
@Data
public class AccountCreateReq {
    /**
     * 商户编号
     */
    @NotBlank(message = "商户编号不能为空")
    @Length(min = 4, message = "商户编号长度不能低于4位")
    private String merchantNo;
    /**
     * 账户类型
     */
    @NotBlank(message = "账户类型不能为空")
    private String accountType;
    /**
     * 风险预存期天数
     */
    @Min(value = 1, message = "风险预存期天数不能小于1")
    private Integer riskDay;
}
