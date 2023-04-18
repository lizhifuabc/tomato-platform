package com.tomato.account.domain.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 账户注销请求
 *
 * @author lizhifu
 * @since  2022/7/1
 */
@Data
public class AccountFreezeReq {
    /**
     * 账户编号
     */
    @NotBlank(message = "账户编号不能为空")
    @Length(min = 4, message = "账户编号长度不能低于4位")
    private String accountNo;

    @NotBlank(message = "账户状态不能为空")
    private String accountStatus;
}
