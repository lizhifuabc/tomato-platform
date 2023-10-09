package com.tomato.web.core.xss;

import org.owasp.validator.html.PolicyException;

import java.io.FileNotFoundException;

/**
 * XssUtil
 *
 * @author lizhifu
 * @since 2023/10/9
 */
public class XssUtilTest {
	public static void main(String[] args) throws PolicyException, FileNotFoundException {
		XssUtil xssUtil = new XssUtil("classpath:antisamy/antisamy-anythinggoes.xml");
		System.out.println("清洗结果："+xssUtil.cleaning("123123"));
	}
}
