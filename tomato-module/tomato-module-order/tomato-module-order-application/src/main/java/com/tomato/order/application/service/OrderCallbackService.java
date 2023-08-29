package com.tomato.order.application.service;

import com.tomato.common.exception.BusinessException;
import com.tomato.order.application.req.OrderCallbackReq;
import com.tomato.order.application.util.HmacUtil;
import com.tomato.order.domain.domain.entity.AccountEntity;
import com.tomato.order.domain.domain.entity.NoticeEntity;
import com.tomato.order.domain.domain.entity.OrderInfoEntity;
import com.tomato.order.domain.repository.AccountRepository;
import com.tomato.order.domain.repository.NoticeRepository;
import com.tomato.order.domain.repository.OrderInfoRepository;
import com.tomato.common.util.BigDecimalUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * 订单接收回调
 *
 * @author lizhifu
 * @since 2023/8/9
 */
@Service
public class OrderCallbackService {

	private final OrderInfoRepository orderInfoRepository;

	private final AccountRepository accountRepository;

	private final NoticeRepository noticeRepository;

	private final MerchantService merchantService;

	// TODO 回调是否要和下单使用同一个线程池
	private final Executor orderAsyncExecutor;

	public OrderCallbackService(OrderInfoRepository orderInfoRepository, AccountRepository accountRepository,
			NoticeRepository noticeRepository, MerchantService merchantService, Executor orderAsyncExecutor) {
		this.orderInfoRepository = orderInfoRepository;
		this.accountRepository = accountRepository;
		this.noticeRepository = noticeRepository;
		this.merchantService = merchantService;
		this.orderAsyncExecutor = orderAsyncExecutor;
	}

	/**
	 * 支付订单回调处理
	 * @param orderCallbackReq 回调参数
	 */
	public void callback(OrderCallbackReq orderCallbackReq) {
		// 查询订单信息
		OrderInfoEntity orderInfoEntity = orderInfoRepository.selectByOrderNo(orderCallbackReq.getOrderNo());
		Optional.ofNullable(orderInfoEntity).orElseThrow(() -> new BusinessException("订单不存在"));
		if (orderInfoEntity.finalStatus()) {
			// 订单已经完成
			return;
		}
		orderInfoEntity.success();
		// 更新订单状态为成功,通知状态为处理中，入账状态为处理中
		orderInfoRepository.updateOrderStatusSuccess(orderInfoEntity);
		// TODO 是否使用MQ消息通知
		CompletableFuture.runAsync(() -> {
			// 发送账户入账
			AccountEntity accountEntity = AccountEntity.builder()
				.merchantNo(orderInfoEntity.getMerchantNo())
				.merchantOrderNo(orderInfoEntity.getMerchantOrderNo())
				.sysNo(orderInfoEntity.getOrderNo())
				.amount(orderInfoEntity.accountAmount())
				.build();
			accountRepository.trad(accountEntity);
		}, orderAsyncExecutor);
		CompletableFuture.runAsync(() -> {
			// 获取商户key
			String merchantKey = merchantService.merchantKey(orderInfoEntity.getMerchantNo());
			// 发送通知
			Map<String, String> noticeParam = new HashMap<>();
			noticeParam.put("orderNo", orderInfoEntity.getOrderNo());
			noticeParam.put("merchantNo", orderInfoEntity.getMerchantNo());
			noticeParam.put("merchantOrderNo", orderInfoEntity.getMerchantOrderNo());
			noticeParam.put("orderStatus", orderInfoEntity.getOrderStatus());
			noticeParam.put("orderAmount", BigDecimalUtil.bigDecimalToString(orderInfoEntity.getRequestAmount()));
			noticeParam.put("hmac", HmacUtil.hmac(noticeParam, merchantKey));
			NoticeEntity noticeEntity = NoticeEntity.builder()
				.merchantNo(orderInfoEntity.getMerchantNo())
				.merchantOrderNo(orderInfoEntity.getMerchantOrderNo())
				.orderNo(orderInfoEntity.getOrderNo())
				.noticeUrl(orderInfoEntity.getNoticeSys())
				.noticeParam(noticeParam)
				.build();
			noticeRepository.createNotice(noticeEntity);
		}, orderAsyncExecutor);
	}

}
