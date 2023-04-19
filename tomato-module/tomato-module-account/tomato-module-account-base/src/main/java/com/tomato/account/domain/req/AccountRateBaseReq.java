package com.tomato.account.domain.req;

import com.tomato.account.enums.AccountHisTypeEnum;
import com.tomato.validator.annotation.CheckEnum;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 账户费率
 *
 * @author lizhifu
 * @since  2022/6/7
 */
@Data
@Tag(name = "账户费率")
public class AccountRateBaseReq {
    /**
     * 费率
     */
    @NotNull(message = "费率不能为空")
    @DecimalMin(value = "0", message = "费率不能小于0")
    @DecimalMax(value = "1", message = "费率不能大于1")
    private BigDecimal rate;
    /**
     * 费率类型
     */
    @NotBlank(message = "费率类型不能为空")
    @CheckEnum(value = AccountHisTypeEnum.class, message = "费率类型不正确")
    private String rateType;
}
