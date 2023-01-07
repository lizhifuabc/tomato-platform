package com.tomato.util.lang;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BigDecimal
 *
 * @author lizhifu
 * @date 2022/6/22
 */
public class BigDecimalUtil {
    /**
     * 乘法，保留两位小数，四舍五入
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return a.multiply(b).setScale(2, RoundingMode.HALF_UP);
    }
}
