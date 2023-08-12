package com.tomato.account.domain.entity;

import com.tomato.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 节假日控制
 *
 * @author lizhifu
 * @since 2023/1/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountWorkEntity extends BaseEntity {
    /**
     * 日期
     */
    private LocalDate workDay;

    /**
     * 0:工作日；1：调休工作日；2：周末休息日；3：法定休息日；
     */
    private int dayType;

}
