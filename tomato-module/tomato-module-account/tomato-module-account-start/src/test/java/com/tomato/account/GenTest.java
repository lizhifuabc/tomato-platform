package com.tomato.account;

/**
 * gen
 *
 * @author lizhifu
 * @since 2023/1/1
 */
public class GenTest {

	public static void main(String[] args) {
		String sql = "create schema `tomato_account_";
		for (int i = 10; i <= 32; i++) {
			System.out.println(sql + "" + i + "`;");
		}
	}

}
