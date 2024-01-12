package com.tomato.merchant.controller;

import com.tomato.common.domain.resp.Resp;
import com.tomato.jpa.domain.service.BaseReadableService;
import com.tomato.jpa.domain.service.BaseWriteableService;
import com.tomato.merchant.domain.entity.MerchantInfo;
import com.tomato.merchant.domain.req.MerchantCreateReq;
import com.tomato.merchant.service.MerchantInfoService;
import com.tomato.web.jpa.controller.AbstractBaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户创建
 *
 * @author lizhifu
 * @since 2022/12/13
 */
@RestController
@RequestMapping
@Tags({ @Tag(name = "商户创建"), })
public class MerchantCreateController extends AbstractBaseController<MerchantInfo, Long> {

	private final MerchantInfoService merchantInfoService;

	public MerchantCreateController(MerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}

	@PostMapping("/merchant/create")
	@Operation(summary = "商户创建", description = "商户创建")
	public Resp<MerchantInfo> create(@Valid @RequestBody MerchantCreateReq merchantCreateReq) {
		return Resp.of(merchantInfoService.save(merchantCreateReq));
	}

	@Override
	public BaseReadableService<MerchantInfo, Long> getReadableService() {
		return this.merchantInfoService;
	}

	@Override
	public BaseWriteableService<MerchantInfo, Long> getWriteableService() {
		return this.merchantInfoService;
	}

}
