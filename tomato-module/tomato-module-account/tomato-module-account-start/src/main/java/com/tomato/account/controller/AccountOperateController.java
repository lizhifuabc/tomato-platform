package com.tomato.account.controller;

import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.vo.req.AccountCancelledReq;
import com.tomato.account.vo.req.AccountCreateReq;
import com.tomato.account.vo.req.AccountFreezeReq;
import com.tomato.account.vo.resp.AccountCreateResp;
import com.tomato.account.vo.enums.AccountStatusTypeEnum;
import com.tomato.account.service.AccountOperateService;
import com.tomato.common.domain.resp.Resp;
import com.tomato.idempotent.annotation.Idempotent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户管理操作
 *
 * @author lizhifu
 * @since 2023/4/18
 */
@RestController
@Tag(name = "账户管理操作", description = "账户管理操作")
public class AccountOperateController {

	private final AccountOperateService accountOperateService;

	public AccountOperateController(AccountOperateService accountOperateService) {
		this.accountOperateService = accountOperateService;
	}

	/**
	 * 创建账户
	 * @param accountCreateReq 账户创建
	 * @return void
	 */
	@PostMapping("/account/create")
	@Idempotent
	@Operation(summary = "创建账户", description = "创建账户")
	public Resp<AccountCreateResp> createAccount(@Valid @RequestBody AccountCreateReq accountCreateReq) {
		AccountInfoEntity account = accountOperateService.createAccount(accountCreateReq);
		AccountCreateResp accountCreateResp = new AccountCreateResp();
		accountCreateResp.setAccountNo(account.getAccountNo());
		return Resp.of(accountCreateResp);
	}

	/**
	 * 注销账户
	 * @param accountCancelledReq 注销账户
	 * @return void
	 */
	@PostMapping("/account/cancelled")
	@Operation(summary = "注销账户", description = "注销账户")
	public Resp<Void> cancelledAccount(@Valid @RequestBody AccountCancelledReq accountCancelledReq) {
		accountOperateService.cancelledAccount(accountCancelledReq);
		return Resp.buildSuccess();
	}

	/**
	 * 冻结止收
	 * @param accountFreezeReq 冻结止收
	 * @return void
	 */
	@PostMapping("/account/freeze/credit")
	@Operation(summary = "冻结止收", description = "冻结止收")
	public Resp<Void> freezeCredit(@Valid @RequestBody AccountFreezeReq accountFreezeReq) {
		accountOperateService.freezeOrUnfreeze(accountFreezeReq,
				AccountStatusTypeEnum.ACCOUNT_FREEZE_CREDIT.getValue());
		return Resp.buildSuccess();
	}

	/**
	 * 冻结止付
	 * @param accountFreezeReq 冻结止付
	 * @return void
	 */
	@PostMapping("/account/freeze/debit")
	@Operation(summary = "冻结止付", description = "冻结止付")
	public Resp<Void> freezeDebit(@Valid @RequestBody AccountFreezeReq accountFreezeReq) {
		accountOperateService.freezeOrUnfreeze(accountFreezeReq, AccountStatusTypeEnum.ACCOUNT_FREEZE_DEBIT.getValue());
		return Resp.buildSuccess();
	}

	/**
	 * 完全冻结
	 * @param accountFreezeReq 完全冻结
	 * @return void
	 */
	@PostMapping("/account/freeze/freeze")
	@Operation(summary = "完全冻结", description = "完全冻结")
	public Resp<Void> freeze(@Valid @RequestBody AccountFreezeReq accountFreezeReq) {
		accountOperateService.freezeOrUnfreeze(accountFreezeReq, AccountStatusTypeEnum.ACCOUNT_FROZEN.getValue());
		return Resp.buildSuccess();
	}

	/**
	 * 解冻
	 * @param accountFreezeReq 解冻
	 * @return void
	 */
	@PostMapping("/account/freeze/unfreeze")
	@Operation(summary = "解冻", description = "解冻")
	public Resp<Void> unfreeze(@Valid @RequestBody AccountFreezeReq accountFreezeReq) {
		accountOperateService.freezeOrUnfreeze(accountFreezeReq, AccountStatusTypeEnum.ACCOUNT_AVAILABLE.getValue());
		return Resp.buildSuccess();
	}

}
