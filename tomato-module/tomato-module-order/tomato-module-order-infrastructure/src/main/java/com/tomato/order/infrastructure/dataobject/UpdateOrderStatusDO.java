package com.tomato.order.infrastructure.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 更新订单状态
 *
 * @author lizhifu
 * @since 2022/12/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOrderStatusDO {

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 订单状态
	 */
	private String orderStatus;

	/**
	 * 期望订单状态，例如由支付中-->支付成功 那么期望订单状态就是支付中
	 */
	private List<String> expectOrderStatus;

	/**
	 * 当前版本号
	 */
	private Integer currentVersion;

	/**
	 * 完成时间
	 */
	private LocalDateTime completeTime;

	/**
	 * 通知状态
	 */
	private String noticeStatus;

	/**
	 * 入账状态
	 */
	private String accountStatus;

}
