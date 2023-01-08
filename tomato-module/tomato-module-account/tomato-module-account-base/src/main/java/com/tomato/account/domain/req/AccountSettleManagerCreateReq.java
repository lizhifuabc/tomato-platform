package com.tomato.account.domain.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 账号结算控制
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Data
public class AccountSettleManagerCreateReq {
    /**
     * 账户编号
     */
    @NotBlank(message = "账户编号不能为空")
    private String accountNo;
    /**
     * 商户编号
     */
    @NotBlank(message = "商户编号不能为空")
    private String merchantNo;
    @Valid
    private AccountSettleCreateReq accountSettleCreateReq;
}
