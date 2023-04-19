package com.tomato.account.controller;

import com.tomato.account.domain.req.AccountCancelledReq;
import com.tomato.account.domain.req.AccountCreateReq;
import com.tomato.account.domain.req.AccountFreezeReq;
import com.tomato.account.service.AccountOperateService;
import com.tomato.domain.resp.Resp;
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
    public Resp<Void> createAccount(@Valid @RequestBody AccountCreateReq accountCreateReq){
        accountOperateService.createAccount(accountCreateReq);
        return Resp.buildSuccess();
    }
    /**
     * 注销账户
     * @param accountCancelledReq 注销账户
     * @return void
     */
    @PostMapping("/account/cancelled")
    @Operation(summary = "注销账户", description = "注销账户")
    public Resp<Void> cancelledAccount(@Valid @RequestBody AccountCancelledReq accountCancelledReq){
        accountOperateService.cancelledAccount(accountCancelledReq);
        return Resp.buildSuccess();
    }
    /**
     * 冻结账户
     * @param accountFreezeReq 冻结账户
     * @return void
     */
    @PostMapping("/account/freeze")
    @Operation(summary = "冻结账户", description = "冻结账户")
    public Resp<Void> freeze(@Valid @RequestBody AccountFreezeReq accountFreezeReq){
        accountOperateService.freezeOrUnfreeze(accountFreezeReq);
        return Resp.buildSuccess();
    }
    /**
     * 解冻账户
     * @param accountFreezeReq 解冻账户
     * @return void
     */
    @PostMapping("/account/unfreeze")
    @Operation(summary = "解冻账户", description = "解冻账户")
    public Resp<Void> unfreeze(@Valid @RequestBody AccountFreezeReq accountFreezeReq){
        accountOperateService.freezeOrUnfreeze(accountFreezeReq);
        return Resp.buildSuccess();
    }
}
