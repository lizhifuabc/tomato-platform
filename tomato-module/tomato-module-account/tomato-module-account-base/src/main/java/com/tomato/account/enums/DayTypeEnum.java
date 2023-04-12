package com.tomato.account.enums;

import com.tomato.domain.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 0:工作日；1：调休工作日；2：周末休息日；3：法定休息日；
 *
 * @author lizhifu
 * @since 2023/1/10
 */
@AllArgsConstructor
@Getter
public enum DayTypeEnum implements BaseEnum {
    /**
     * 0:工作日
     */
    WORK_DAY(0,"工作日"),
    /**
     * 1：调休工作日
     */
    REST_WORK_DAY(1,"调休工作日"),
    /**
     *2：周末休息日
     */
    WEEK_REST_DAY(2,"周末休息日"),
    /**
     * 3：法定休息日
     */
    LEGAL_REST_DAY(3,"法定休息日"),
    ;
    private final Integer value;

    private final String desc;

    public static DayTypeEnum fromValue(Integer value){
        return Arrays.stream(DayTypeEnum.values()).filter(dayTypeEnum -> dayTypeEnum.getValue().equals(value)).findFirst().orElse(null);
    }
}
