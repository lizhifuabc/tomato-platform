package com.tomato.account.controller;

import com.tomato.account.domain.req.AccountTradReq;
import com.tomato.account.service.AccountAsyncInitService;
import com.tomato.account.service.trad.AccountTradService;
import com.tomato.domain.resp.Resp;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户入账
 *
 * @author lizhifu
 * @since 2023/1/7
 */
@RestController
@Slf4j
@Tag(name = "账户入账", description = "账户入账")
public class AccountTradController {
    @Resource(name = "accountAddService")
    private AccountTradService accountTradService;
    @Resource
    private AccountAsyncInitService accountAsyncInitService;
    /**
     * 账户入账
     * @param accountTradReq 入账请求
     * @return Resp 账户入账结果
     */
    @PostMapping("/account/trad")
    @Schema(description = "账户入账")
    public Resp<Void> trad(@Validated @RequestBody AccountTradReq accountTradReq){
        log.info("账户入账 start :{}",accountTradReq);
        boolean async = accountAsyncInitService.checkMerchantNo(accountTradReq.getMerchantNo());
        if(async){
            accountTradService.exeAsync(accountTradReq);
        }else {
            accountTradService.exe(accountTradReq);
        }
        log.info("账户入账 end :{}",accountTradReq);
        return Resp.buildSuccess();
    }
}
