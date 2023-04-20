package com.tomato.account.domain.resp;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

/**
 * 账户创建返回
 *
 * @author lizhifu
 * @since 2023/4/20
 */
@Data
@Tag(name = "账户创建返回", description = "账户创建返回")
public class AccountCreateResp {
    /**
     * 账户编号
     */
    private String accountNo;
}
