package com.tomato.merchant.service;

import com.google.common.base.Strings;
import org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 商户编号生成 10:20230101:随机数
 *
 * @author lizhifu
 * @since 2023/1/1
 */
@Service
public class MerchantNoService {

	private static final String PREFIX = "10";

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

	private final MySQLMaxValueIncrementer merchantIncrementer;

	public MerchantNoService(MySQLMaxValueIncrementer merchantIncrementer) {
		this.merchantIncrementer = merchantIncrementer;
	}

	public String nextStringValue() {
		String date = LocalDate.now().format(DATE_FORMATTER);
		String paddedLong = Strings.padStart(merchantIncrementer.nextStringValue(), 7, '0');
		return PREFIX + date + paddedLong;
	}

}
