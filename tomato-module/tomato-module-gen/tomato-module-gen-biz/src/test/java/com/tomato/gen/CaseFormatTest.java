package com.tomato.gen;

import com.google.common.base.CaseFormat;

/**
 * CaseFormat
 *
 * @author lizhifu
 */
public class CaseFormatTest {
    public static void main(String[] args) {
        // 连接符转驼峰，首字母小写 结果testData
        System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, "test-data"));

        // 连接符转驼峰，首字母大写 结果TestData
        System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, "test-data-a"));

        // 下划线转驼峰，首字母小写 结果testData
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "test_data"));

        // 下划线转驼峰，首字母大写 结果TestData
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "test_data"));

        // 驼峰转小写下划线 结果test_data
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "testData"));

        // 驼峰转大写下划线 结果TEST_DATA
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, "TestData"));

        // 驼峰转小写连接符 结果test-data
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, "testData"));
    }
}
