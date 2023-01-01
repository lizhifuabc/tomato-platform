package com.tomato.merchant.service;

import com.tomato.util.StrUtil;
import com.tomato.util.date.DatePattern;
import org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * 商户编号生成
 * 10:20230101:随机数
 * @author lizhifu
 * @since 2023/1/1
 */
@Service
public class MerchantNoService {
    private final MySQLMaxValueIncrementer merchantIncrementer;

    public MerchantNoService(MySQLMaxValueIncrementer merchantIncrementer) {
        this.merchantIncrementer = merchantIncrementer;
    }

    public String nextStringValue(){
        String date = LocalDate.now().format(DatePattern.PURE_DATE_FORMATTER);
        return "10" + date + StrUtil.fillAfter("0",merchantIncrementer.nextStringValue(),7);
    }
}
