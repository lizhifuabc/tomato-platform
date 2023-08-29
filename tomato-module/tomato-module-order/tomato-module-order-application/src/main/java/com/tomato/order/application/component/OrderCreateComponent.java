package com.tomato.order.application.component;

import com.tomato.order.application.req.OrderCreateReq;
import com.tomato.order.application.service.MerchantService;
import com.tomato.order.application.util.HmacUtil;
import com.tomato.order.domain.constants.OrderStatusEnum;
import com.tomato.order.domain.domain.entity.MerchantEntity;
import com.tomato.order.domain.domain.entity.OrderInfoEntity;
import com.tomato.order.domain.repository.OrderInfoRepository;
import org.springframework.stereotype.Component;

/**
 * 订单创建组件
 *
 * @author lizhifu
 * @since 2023/8/2
 */
@Component
public class OrderCreateComponent {

	private final OrderInfoRepository orderInfoRepository;

	private final MerchantService merchantService;

	private final OrderNoComponent orderNoComponent;

	public OrderCreateComponent(OrderInfoRepository orderInfoRepository, MerchantService merchantService,
			OrderNoComponent orderNoComponent) {
		this.orderInfoRepository = orderInfoRepository;
		this.merchantService = merchantService;
		this.orderNoComponent = orderNoComponent;
	}

	/**
	 * 订单新建 TODO 分库之后是否需要加分布式锁
	 * @param orderCreateReq 订单新建
	 */
	public OrderInfoEntity createOrder(OrderCreateReq orderCreateReq, String clientIp,
			OrderStatusEnum orderStatusEnum) {
		MerchantEntity query = MerchantEntity.builder()
			.merchantNo(orderCreateReq.getMerchantNo())
			.payType(orderCreateReq.getPayType())
			.build();
		MerchantEntity merchantEntity = merchantService.merchant(query);
		OrderInfoEntity orderInfoEntity = OrderInfoEntity.builder()
			.orderStatus(orderStatusEnum.getValue())
			.noticeWeb(orderCreateReq.getNoticeWeb())
			.noticeSys(orderCreateReq.getNoticeSys())
			.payType(orderCreateReq.getPayType())
			.merchantEntity(merchantEntity)
			.requestAmount(orderCreateReq.getRequestAmount())
			.merchantOrderNo(orderCreateReq.getMerchantOrderNo())
			.merchantNo(orderCreateReq.getMerchantNo())
			.extParam(orderCreateReq.getExtParam())
			.clientIp(clientIp)
			.hmac(orderCreateReq.getHmac())
			.orderNo(orderNoComponent.createOrderNo(orderCreateReq.getMerchantNo()))
			.build();
		// 校验 hmac TODO
		// orderInfoEntity.checkHmac(HmacUtil.hmac(orderCreateReq,merchantEntity.getMerchantKey()));
		// 初始化订单实体
		orderInfoEntity.init();
		orderInfoRepository.createOrder(orderInfoEntity);
		return orderInfoEntity;
	}

}
