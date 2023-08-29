package com.tomato.order.application.req;

import com.tomato.module.common.enums.PayTypeEnum;
import com.tomato.order.application.req.base.BaseReq;
import com.tomato.validator.annotation.CheckEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 订单新建
 *
 * @author lizhifu
 * @since 2023/8/1
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Tag(name = "订单新建", description = "订单新建")
public class OrderCreateReq extends BaseReq {

	/**
	 * 订单金额
	 */
	@Schema(description = "订单金额")
	@NotNull(message = "订单金额不能为为空！")
	@DecimalMin(value = "0.01", message = "订单金额不能小于0.01")
	@Digits(integer = 9, fraction = 2, message = "订单金额格式不正确")
	private BigDecimal requestAmount;

	/**
	 * 支付方式：1 微信扫码 、2 支付宝扫码
	 */
	@Schema(description = "支付方式")
	@CheckEnum(value = PayTypeEnum.class, message = "支付方式不正确")
	private int payType;

	/**
	 * 商户订单号
	 */
	@Schema(description = "商户订单号")
	@NotBlank(message = "商户订单号不能为空！")
	@Length(max = 32, message = "商户订单号不能超过32位")
	private String merchantOrderNo;

	/**
	 * 页面通知地址
	 */
	@Schema(description = "页面通知地址")
	private String noticeWeb;

	/**
	 * 系统通知地址
	 */
	@Schema(description = "系统通知地址")
	@NotBlank(message = "系统通知地址不能为空！")
	@Length(max = 128, message = "系统通知地址不能超过128位")
	private String noticeSys;

	/**
	 * 商户扩展参数
	 */
	@Schema(description = "商户扩展参数")
	private String extParam;

}
