package com.tomato.account.controller;

import com.tomato.account.domain.req.AccountTradReq;
import com.tomato.domain.resp.Resp;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户交易
 *
 * @author lizhifu
 * @since 2023/1/7
 */
@RestController
public class AccountTradController {

    public Resp trad(@Validated @RequestBody AccountTradReq accountTradReq){
        return Resp.buildSuccess();
    }
}
