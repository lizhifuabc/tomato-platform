package com.tomato.account.domain.entity;

import com.tomato.domain.entity.BaseEntity;
import lombok.Data;

/**
 * 账户管理历史表
 *
 * @author lizhifu
 * @since 2023/1/7
 */
@Data
public class AccountManageHisEntity extends BaseEntity {
    /**
     * 账户编号
     */
    private String accountNo;

    /**
     * 账户管理顺序号
     */
    private Long accountManageSerial;

    /**
     * 改变前值
     */
    private String beforeValue;

    /**
     * 改变后值
     */
    private String afterValue;
}
