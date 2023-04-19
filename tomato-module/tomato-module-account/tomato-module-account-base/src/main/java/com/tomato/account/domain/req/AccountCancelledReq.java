package com.tomato.account.domain.req;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "账户注销请求", description = "账户注销请求")
public class AccountCancelledReq {
    /**
     * 账户编号
     */
    @NotBlank(message = "账户编号不能为空")
    @Length(min = 4, message = "账户编号长度不能低于4位")
    @Schema(description = "账户编号")
    private String accountNo;
}
