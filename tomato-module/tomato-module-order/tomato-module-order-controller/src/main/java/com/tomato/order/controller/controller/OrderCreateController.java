package com.tomato.order.controller.controller;

import com.tomato.common.resp.Resp;
import com.tomato.order.application.event.OrderCreateEvent;
import com.tomato.order.application.req.OrderCreateReq;
import com.tomato.order.application.resp.OrderScanCreateResp;
import com.tomato.order.application.service.OrderCreateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单新建
 *
 * @author lizhifu
 * @since 2022/12/20
 */
@RestController
@Slf4j
@Tag(name = "订单接口", description = "订单接口")
public class OrderCreateController {

	private final OrderCreateService orderCreateService;

	public OrderCreateController(OrderCreateService orderCreateService) {
		this.orderCreateService = orderCreateService;
	}

	/**
	 * 订单新建:扫码
	 * @param orderCreateReq 订单新建
	 * @return Resp Void
	 */
	@Operation(summary = "扫码收单", description = "扫码收单")
	@PostMapping("/order/create/scan")
	public Resp<OrderScanCreateResp> createScanOrder(@Valid @RequestBody OrderCreateReq orderCreateReq,
			HttpServletRequest request) {
		log.info("扫码收单:{}", orderCreateReq.getMerchantOrderNo());
		OrderScanCreateResp orderScanCreateResp = orderCreateService.createScanOrder(orderCreateReq,
				request.getRemoteAddr());
		return Resp.of(orderScanCreateResp);
	}

}
