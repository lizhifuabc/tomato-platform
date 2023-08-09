package com.tomato.account.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 账户注销请求
 *
 * @author lizhifu
 * @since  2022/7/1
 */
@Data
@Tag(name = "账户注销请求", description = "账户注销请求")
public class AccountCancelledReq {
    /**
     * 账户编号
     */
    @NotBlank(message = "账户编号不能为空")
    @Schema(description = "账户编号")
    private String accountNo;
}
