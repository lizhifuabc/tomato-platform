package com.tomato.account.vo.req;

import com.tomato.account.enums.CycleTypeEnum;
import com.tomato.account.enums.SettleTargetTypeEnum;
import com.tomato.account.enums.SettleTypeEnum;
import com.tomato.validator.annotation.CheckEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * 账户结算规则
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Data
@Tag(name = "账户结算规则")
public class AccountSettleCreateReq {
    /**
     * 账户编号
     */
    @NotBlank(message = "账户编号不能为空")
    @Schema(description = "账户编号")
    private String accountNo;
    /**
     * 结算类型
     */
    @NotBlank(message = "结算类型不能为空")
    @Schema(description = "结算类型")
    @CheckEnum(value = SettleTypeEnum.class,message = "结算类型不正确")
    private String settleType;

    /**
     * 结算周期
     */
    @NotBlank(message = "结算周期不能为空")
    @Schema(description = "结算周期")
    @CheckEnum(value = CycleTypeEnum.class,message = "结算周期不正确")
    private String cycleType;

    /**
     * 结算周期数据
     */
    @Schema(description = "结算周期数据")
    private String cycleData;

    /**
     * 风险预存期
     */
    @Min(value = 1,message = "风险预存期最小为1")
    @Max(value = 3,message = "风险预存期最大为3")
    @NotNull
    @Schema(description = "风险预存期")
    private Integer reserveDays;

    /**
     * 最大结算天数
     */
    @Min(value = 1,message = "最大结算天数最小为1")
    @NotNull
    @Schema(description = "最大结算天数")
    private Integer maxSettleDays;

    /**
     * 结算到目标账户类型
     */
    @NotBlank(message = "结算到目标账户类型不能为空")
    @Schema(description = "结算到目标账户类型")
    @CheckEnum(value = SettleTargetTypeEnum.class,message = "结算到目标账户类型不正确")
    private String settleTargetType;
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}
