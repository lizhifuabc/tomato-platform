package com.tomato.order.infrastructure.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 延迟订单
 *
 * @author lizhifu
 * @since 2022/12/26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDelayDO {

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 订单失效时间
	 */
	private LocalDateTime timeoutTime;

}
