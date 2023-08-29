package com.tomato.notice.controller;

import com.tomato.common.resp.Resp;
import com.tomato.notice.dto.req.NoticeCreateReq;
import com.tomato.notice.service.service.NoticeRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通知收单
 *
 * @author lizhifu
 * @since 2023/1/5
 */
@RestController
@Slf4j
@Tag(name = "通知收单", description = "通知收单")
public class NoticeCreateController {

	private final NoticeRecordService noticeRecordService;

	public NoticeCreateController(NoticeRecordService noticeRecordService) {
		this.noticeRecordService = noticeRecordService;
	}

	@PostMapping("/notice/create")
	@Operation(summary = "通知收单", description = "通知收单")
	public Resp<Void> createNotice(@Validated @RequestBody NoticeCreateReq noticeCreateReq) {
		log.info("通知收单 merchantNo:{},merchantOrderNo:{},ruleCode:{}", noticeCreateReq.getMerchantNo(),
				noticeCreateReq.getMerchantOrderNo(), noticeCreateReq.getRuleCode());
		// TODO 通知规则校验
		// TODO 商户通知黑名单：三个级别：1：直接返回，2：抛出异常，3：保存到数据库
		noticeRecordService.createNotice(noticeCreateReq);
		return Resp.buildSuccess();
	}

	@PostMapping("/notice/create/async")
	@Operation(summary = "异步通知收单", description = "异步通知收单")
	public Resp<Void> createNoticeAsync(@Validated @RequestBody NoticeCreateReq noticeCreateReq) {
		log.info("异步通知收单 merchantNo:{},merchantOrderNo:{},ruleCode:{}", noticeCreateReq.getMerchantNo(),
				noticeCreateReq.getMerchantOrderNo(), noticeCreateReq.getRuleCode());
		// TODO 通知规则校验
		// TODO 商户通知黑名单：三个级别：1：直接返回，2：抛出异常，3：保存到数据库
		noticeRecordService.createNoticeAsync(noticeCreateReq);
		return Resp.buildSuccess();
	}

}
