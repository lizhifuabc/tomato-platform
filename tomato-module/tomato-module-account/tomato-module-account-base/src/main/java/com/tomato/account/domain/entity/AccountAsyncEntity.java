package com.tomato.account.domain.entity;

import com.tomato.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 异步入账账户
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountAsyncEntity extends BaseEntity {
    /**
     * 账户编号
     */
    private String accountNo;
    /**
     * 商户编号
     */
    private String merchantNo;
}
