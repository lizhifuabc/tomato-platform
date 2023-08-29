package com.tomato.order.infrastructure.repository.impl;

import com.tomato.common.resp.Resp;
import com.tomato.notice.api.RemoteNoticeService;
import com.tomato.notice.dto.req.NoticeCreateReq;
import com.tomato.order.domain.constants.OrderStatusEnum;
import com.tomato.order.domain.domain.entity.NoticeEntity;
import com.tomato.order.domain.repository.NoticeRepository;
import com.tomato.order.infrastructure.mapper.OrderInfoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

/**
 * 通知系统
 *
 * @author lizhifu
 * @since 2023/8/12
 */
@Repository
@Slf4j
public class NoticeRepositoryImpl implements NoticeRepository {

	private final RemoteNoticeService remoteNoticeService;

	private final OrderInfoMapper orderInfoMapper;

	public NoticeRepositoryImpl(RemoteNoticeService remoteNoticeService, OrderInfoMapper orderInfoMapper) {
		this.remoteNoticeService = remoteNoticeService;
		this.orderInfoMapper = orderInfoMapper;
	}

	@Override
	public void createNotice(NoticeEntity noticeEntity) {
		NoticeCreateReq noticeCreateReq = getNoticeCreateReq(noticeEntity);
		Resp<Void> notice = remoteNoticeService.createNotice(noticeCreateReq);
		if (!notice.isSuccess()) {
			// TODO 重试或者记录日志
			log.error("通知系统失败:{},流水号:{}", notice.getMsg(), noticeEntity.getOrderNo());
			orderInfoMapper.updateNoticeStatus(noticeEntity.getOrderNo(), OrderStatusEnum.FAIL.getValue());
			return;
		}
		orderInfoMapper.updateNoticeStatus(noticeEntity.getOrderNo(), OrderStatusEnum.SUCCESS.getValue());
	}

	@NotNull
	private static NoticeCreateReq getNoticeCreateReq(NoticeEntity noticeEntity) {
		NoticeCreateReq noticeCreateReq = new NoticeCreateReq();
		noticeCreateReq.setMerchantNo(noticeEntity.getMerchantNo());
		noticeCreateReq.setMerchantOrderNo(noticeEntity.getMerchantOrderNo());
		noticeCreateReq.setNoticeUrl(noticeEntity.getNoticeUrl());
		noticeCreateReq.setHttpMethod("POST");
		noticeCreateReq.setAppNo("tomato-order");
		noticeCreateReq.setNoticeParam(noticeEntity.getNoticeParam());
		noticeCreateReq.setOrderNo(noticeEntity.getOrderNo());
		noticeCreateReq.setRuleCode("tomato-order");
		return noticeCreateReq;
	}

}
