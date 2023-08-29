package com.tomato.merchant.controller;

import com.tomato.common.resp.Resp;
import com.tomato.merchant.domain.entity.MerchantInfo;
import com.tomato.merchant.domain.req.MerchantConfigQueryReq;
import com.tomato.merchant.domain.req.MerchantConfigReq;
import com.tomato.merchant.domain.resp.MerchantConfigQueryResp;
import com.tomato.merchant.service.MerchantConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户配置
 *
 * @author lizhifu
 * @since 2023/7/24
 */
@RestController
@RequestMapping
@Tags({ @Tag(name = "商户配置"), })
public class MerchantConfigController {

	private final MerchantConfigService merchantConfigService;

	public MerchantConfigController(MerchantConfigService merchantConfigService) {
		this.merchantConfigService = merchantConfigService;
	}

	@PostMapping("/merchant/config")
	@Operation(summary = "商户配置", description = "商户配置")
	public Resp<Void> create(@Valid @RequestBody MerchantConfigReq merchantConfigReq) {
		merchantConfigService.create(merchantConfigReq);
		return Resp.buildSuccess();
	}

	@PostMapping("/merchant/config/query")
	@Operation(summary = "商户配置查询", description = "商户配置查询")
	public Resp<MerchantConfigQueryResp> query(@Valid @RequestBody MerchantConfigQueryReq merchantConfigReq) {
		MerchantConfigQueryResp merchantConfigQueryResp = merchantConfigService.query(merchantConfigReq);
		return Resp.of(merchantConfigQueryResp);
	}

}
