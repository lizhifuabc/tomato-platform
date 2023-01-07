package com.tomato.account.constant;

import com.tomato.domain.resp.code.RespCode;

/**
 * 账户相关异常类
 *
 * @author lizhifu
 * @since 2023/1/7
 */
public class AccountRespCode {
    public static final RespCode ACCOUNT_ALREADY_EXIST = new RespCode("FA010001","账号已经存在");
    public static final RespCode ACCOUNT_CANCEL_FAIL = new RespCode("FA010002","账户注销失败");
    public static final RespCode ACCOUNT_NOT_EXIST = new RespCode("FA020001","账户不存在");
    public static final RespCode ACCOUNT_STATUS_NOT_ACTIVE = new RespCode("FA020003","账户状态不可用");
    public static final RespCode AVAILABLE_BALANCE_NOT_ENOUGH = new RespCode("FA020004","余额不足");
}
