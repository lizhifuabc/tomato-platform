package com.tomato.order.domain.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * 通知系统
 *
 * @author lizhifu
 * @since 2023/8/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeEntity {

	/**
	 * 订单号
	 */
	@Schema(description = "订单号")
	private String orderNo;

	/**
	 * 商户编号
	 */
	@Schema(description = "商户编号")
	private String merchantNo;

	/**
	 * 商户订单号
	 */
	@Schema(description = "商户订单号")
	private String merchantOrderNo;

	/**
	 * 通知地址
	 */
	@Schema(description = "通知地址")
	private String noticeUrl;

	/**
	 * 通知参数
	 */
	@Schema(description = "通知参数")
	private Map<String, String> noticeParam;

}
