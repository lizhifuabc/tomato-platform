package com.tomato.merchant;

import com.tomato.util.StrUtil;
import com.tomato.util.date.DatePattern;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * test
 *
 * @author lizhifu
 * @since 2023/1/1
 */
public class Test {
    public static void main(String[] args) {
        String date = LocalDate.now().format(DatePattern.PURE_DATE_FORMATTER);
        String genNo = "123";
        String no = "10" + date + StrUtil.fillAfter("0",genNo,7);
        // 20230101
        System.out.println(no);
    }
}
