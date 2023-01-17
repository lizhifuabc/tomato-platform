package com.tomato.account.domain.req;

import com.tomato.account.enums.AccountTypeEnum;
import com.tomato.validator.annotation.CheckEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 账户创建请求
 *
 * @author lizhifu
 * @since  2022/7/1
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
    @CheckEnum(value = AccountTypeEnum.class, message = "账户类型错误")
    private String accountType;
}
