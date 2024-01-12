package com.tomato.account.controller;

import com.tomato.account.vo.req.AccountRateReqList;
import com.tomato.account.manager.AccountRateManager;
import com.tomato.common.domain.resp.Resp;
import com.tomato.idempotent.annotation.Idempotent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户费率
 *
 * @author lizhifu
 * @since 2023/1/7
 */
@RestController
@Slf4j
@Tag(name = "账户费率", description = "账户费率")
public class AccountRateController {

	private final AccountRateManager accountRateManager;

	public AccountRateController(AccountRateManager accountRateManager) {
		this.accountRateManager = accountRateManager;
	}

	/**
	 * 账户费率
	 * @param accountRateReqList 账户费率
	 * @return Resp 账户费率结果
	 */
	@PostMapping("/account/rate/init")
	@Idempotent(timeout = 60)
	@Operation(summary = "账户费率初始化", description = "账户费率初始化")
	public Resp<Void> init(@Validated @RequestBody AccountRateReqList accountRateReqList) {
		log.info("账户费率 start :{}", accountRateReqList);
		accountRateManager.init(accountRateReqList);
		log.info("账户费率 end :{}", accountRateReqList);
		return Resp.buildSuccess();
	}

}
