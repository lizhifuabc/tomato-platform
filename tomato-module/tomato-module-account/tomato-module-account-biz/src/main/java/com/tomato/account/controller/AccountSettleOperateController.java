package com.tomato.account.controller;

import com.tomato.account.domain.req.AccountSettleCreateReq;
import com.tomato.account.service.AccountSettleOperateService;
import com.tomato.common.resp.Resp;
import com.tomato.idempotent.annotation.Idempotent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户结算规则
 *
 * @author lizhifu
 * @since 2023/1/7
 */
@RestController
@Slf4j
@Tag(name = "账户结算规则", description = "账户结算规则")
public class AccountSettleOperateController {
    private final AccountSettleOperateService accountSettleOperateService;

    public AccountSettleOperateController(AccountSettleOperateService accountSettleOperateService) {
        this.accountSettleOperateService = accountSettleOperateService;
    }

    /**
     * 账户结算规则
     * @param accountSettleCreateReq 账户结算规则
     * @return Resp 账户结算规则结果
     */
    @PostMapping("/account/settle/init")
    @Idempotent
    @Operation(summary = "账户结算规则初始化",description = "账户结算规则初始化")
    public Resp<Void> init(@Validated @RequestBody AccountSettleCreateReq accountSettleCreateReq){
        log.info("账户结算规则 start :{}",accountSettleCreateReq);
        accountSettleOperateService.create(accountSettleCreateReq);
        log.info("账户结算规则 end :{}",accountSettleCreateReq);
        return Resp.buildSuccess();
    }

    /**
     * 更改账户结算规则
     * @param accountSettleCreateReq 更改账户结算规则
     * @return Resp 更改账户结算规则
     */
    @PostMapping("/account/settle/update")
    @Idempotent
    @Operation(summary = "更改账户结算规则",description = "更改账户结算规则")
    public Resp<Void> update(@Validated @RequestBody AccountSettleCreateReq accountSettleCreateReq){
        log.info("更改账户结算规则 start :{}",accountSettleCreateReq);
        accountSettleOperateService.update(accountSettleCreateReq);
        log.info("更改账户结算规则 end :{}",accountSettleCreateReq);
        return Resp.buildSuccess();
    }
}
