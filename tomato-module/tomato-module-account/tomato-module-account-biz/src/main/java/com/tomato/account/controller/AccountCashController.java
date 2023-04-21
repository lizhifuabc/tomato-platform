package com.tomato.account.controller;

import com.tomato.account.constant.AccountRespCode;
import com.tomato.account.domain.dto.AccountTradDto;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.req.AccountCashReq;
import com.tomato.account.enums.AccountHisTypeEnum;
import com.tomato.account.manager.AccountInfoManager;
import com.tomato.account.service.AccountAsyncInitService;
import com.tomato.account.service.trad.AccountTradService;
import com.tomato.common.exception.BusinessException;
import com.tomato.domain.resp.Resp;
import com.tomato.idempotent.annotation.Idempotent;
import com.tomato.web.common.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 账户提现
 *
 * @author lizhifu
 * @since 2023/1/7
 */
@RestController
@Slf4j
@Tag(name = "账户提现", description = "账户提现")
public class AccountCashController extends BaseController {
    @Resource(name = "accountDeductService")
    private AccountTradService accountTradService;
    @Resource
    private AccountAsyncInitService accountAsyncInitService;
    @Resource
    private AccountInfoManager accountInfoManager;
    /**
     * 账户提现(商户后台) TODO 短信等等安全校验 银行卡校验
     * @param accountCashReq 账户提现请求
     * @return Resp 账户提现结果
     */
    @PostMapping("/account/cash")
    @Idempotent
    @Operation(summary = "账户提现(商户后台)",description = "账户提现(商户后台)")
    public Resp<Void> cash(@Validated @RequestBody AccountCashReq accountCashReq){
        log.info("账户提现 start :{}",accountCashReq);
        String sysNo = UUID.randomUUID().toString().replace("-", "");
        boolean async = accountAsyncInitService.check(accountCashReq.getAccountNo());

        AccountTradDto accountTradDto = copy(accountCashReq, AccountTradDto.class);
        accountTradDto.setAccountHisType(AccountHisTypeEnum.CASH.getValue());
        accountTradDto.setSysNo(sysNo);
        accountTradDto.setMerchantOrderNo(sysNo);
        AccountInfoEntity account = accountInfoManager.selectByAccountNo(accountCashReq.getAccountNo()).orElseThrow(()-> new BusinessException(AccountRespCode.ACCOUNT_NOT_EXIST));
        accountTradDto.setAccountNo(account.getAccountNo());
        accountTradDto.setAmount(accountCashReq.getAmount().negate());
        accountTradDto.setRemark("账户提现");

        if(async){
            accountTradService.exeAsync(accountTradDto);
        }else {
            accountTradService.exe(accountTradDto);
        }
        log.info("账户提现 end :{}",accountCashReq);
        return Resp.buildSuccess();
    }
}
