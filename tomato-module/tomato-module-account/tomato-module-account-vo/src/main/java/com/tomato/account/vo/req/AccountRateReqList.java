package com.tomato.account.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

/**
 * 账户费率
 *
 * @author lizhifu
 * @since  2022/6/7
 */
@Data
@Tag(name = "账户费率")
public class AccountRateReqList {
    /**
     * 账户编号
     */
    @NotBlank(message = "账户编号不能为空")
    @Schema(description = "账户编号")
    private String accountNo;
    /**
     * 账户费率明细
     */
    @NotNull(message = "账户费率明细不能为空")
    @Size(min = 1, message = "账户费率明细不能为空")
    @Schema(description = "账户费率明细")
    @Valid
    private List<AccountRateBaseReq> accountRateBaseReqList;
}
