package com.tomato.account.controller;

import com.tomato.account.constant.AccountRespCode;
import com.tomato.account.domain.dto.AccountTradDto;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.vo.req.AccountTradReq;
import com.tomato.account.manager.AccountInfoManager;
import com.tomato.account.service.AccountAsyncInitService;
import com.tomato.account.service.trad.AccountTradService;
import com.tomato.common.exception.BusinessException;
import com.tomato.common.resp.Resp;
import com.tomato.idempotent.annotation.Idempotent;
import com.tomato.web.core.common.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping
public class AccountTradController extends BaseController {
    @Resource(name = "accountAddService")
    private AccountTradService accountTradService;
    @Resource
    private AccountAsyncInitService accountAsyncInitService;
    @Resource
    private AccountInfoManager accountInfoManager;
    /**
     * 账户入账
     * @param accountTradReq 入账请求
     * @return Resp 账户入账结果
     */
    @PostMapping("/account/trad")
    @Idempotent(timeout = 60)
    @Operation(summary = "账户入账",description = "账户入账")
    public Resp<Void> trad(@Validated @RequestBody AccountTradReq accountTradReq){
        log.info("账户入账 start :{}",accountTradReq);
        boolean async = accountAsyncInitService.checkMerchantNo(accountTradReq.getMerchantNo());
        AccountTradDto accountTradDto = copy(accountTradReq, AccountTradDto.class);
        AccountInfoEntity account = accountInfoManager.selectByMerchantNo(accountTradReq.getMerchantNo(),accountTradReq.getAccountType())
                .orElseThrow(() -> new BusinessException(AccountRespCode.ACCOUNT_NOT_EXIST));
        accountTradDto.setAccountNo(account.getAccountNo());
        if(async){
            accountTradService.exeAsync(accountTradDto);
        }else {
            accountTradService.exe(accountTradDto);
        }
        log.info("账户入账 end :{}",accountTradReq);
        return Resp.buildSuccess();
    }
}
