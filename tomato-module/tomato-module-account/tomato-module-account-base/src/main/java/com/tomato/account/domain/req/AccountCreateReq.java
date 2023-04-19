package com.tomato.account.domain.req;

import com.tomato.account.enums.AccountTypeEnum;
import com.tomato.validator.annotation.CheckEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "账户创建请求", description = "账户创建请求")
public class AccountCreateReq {
    /**
     * 商户编号
     */
    @NotBlank(message = "商户编号不能为空")
    @Length(min = 4, message = "商户编号长度不能低于4位")
    @Schema(description = "商户编号")
    private String merchantNo;
    /**
     * 账户类型
     */
    @NotBlank(message = "账户类型不能为空")
    @CheckEnum(value = AccountTypeEnum.class, message = "账户类型错误")
    @Schema(description = "账户类型")
    private String accountType;
}
