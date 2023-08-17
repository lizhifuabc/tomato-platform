package com.tomato.account.domain.bo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 查询未入账的账户历史
 *
 * @author lizhifu
 * @since 2023/8/17
 */
@Data
public class AccountHisDealQueryBO {
    private String accountNo;
    private Integer limit;
    private LocalDateTime start = LocalDate.now().minusDays(3).atTime(LocalTime.MIN);;
    private LocalDateTime end = LocalDate.now().atTime(LocalTime.MAX);
}
