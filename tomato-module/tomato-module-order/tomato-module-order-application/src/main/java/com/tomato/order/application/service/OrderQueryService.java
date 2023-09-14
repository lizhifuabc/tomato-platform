package com.tomato.order.application.service;

import com.tomato.common.exception.BusinessException;
import com.tomato.order.application.req.OrderQueryByMerchantReq;
import com.tomato.order.application.req.OrderQueryByOrderNoReq;
import com.tomato.order.application.req.OrderQueryResultResp;
import com.tomato.order.application.util.HmacUtil;
import com.tomato.order.domain.domain.entity.OrderInfoEntity;
import com.tomato.order.domain.repository.OrderInfoRepository;
import com.tomato.web.core.util.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单查询
 *
 * @author lizhifu
 * @since 2022/12/1
 */
@Service
public class OrderQueryService {

	private final OrderInfoRepository orderInfoRepository;
	private final MerchantService merchantService;

	public OrderQueryService(OrderInfoRepository orderInfoRepository, MerchantService merchantService) {
		this.orderInfoRepository = orderInfoRepository;
		this.merchantService = merchantService;
	}

	public OrderQueryResultResp orderQueryByMerchant(OrderQueryByMerchantReq orderQueryByMerchantReq) {
		// hmac 校验
		String merchantKey = merchantService.merchantKey(orderQueryByMerchantReq.getMerchantNo());
		Map<String,String> map = new HashMap<>(16);
		map.put("merchantNo",orderQueryByMerchantReq.getMerchantNo());
		map.put("merchantOrderNo",orderQueryByMerchantReq.getMerchantOrderNo());
		String hmac = HmacUtil.hmac(map, merchantKey);
		if (!hmac.equals(orderQueryByMerchantReq.getHmac())) {
			throw new BusinessException("hmac校验失败");
		}
		OrderInfoEntity orderInfoEntity = orderInfoRepository.selectByMerchant(orderQueryByMerchantReq.getMerchantNo(),
				orderQueryByMerchantReq.getMerchantOrderNo());
		return convert(orderInfoEntity);
	}

	public OrderQueryResultResp orderQueryByOrderNo(OrderQueryByOrderNoReq orderQueryByOrderNoReq) {
		// hmac 校验
		String merchantKey = merchantService.merchantKey(orderQueryByOrderNoReq.getMerchantNo());
		Map<String,String> map = new HashMap<>(16);
		map.put("merchantNo",orderQueryByOrderNoReq.getMerchantNo());
		map.put("orderNo",orderQueryByOrderNoReq.getOrderNo());
		String hmac = HmacUtil.hmac(map, merchantKey);
		if (!hmac.equals(orderQueryByOrderNoReq.getHmac())) {
			throw new BusinessException("hmac校验失败");
		}
		OrderInfoEntity orderInfoEntity = orderInfoRepository.selectByOrderNo(orderQueryByOrderNoReq.getMerchantNo(),
				orderQueryByOrderNoReq.getOrderNo());
		return convert(orderInfoEntity);
	}

	private OrderQueryResultResp convert(OrderInfoEntity orderInfoEntity) {
		return BeanUtil.copy(orderInfoEntity, OrderQueryResultResp.class);
	}

}
