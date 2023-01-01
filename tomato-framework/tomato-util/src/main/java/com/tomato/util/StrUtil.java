package com.tomato.util;

/**
 * 字符串工具类
 *
 * @author lizhifu
 * @since 2023/1/1
 */
public class StrUtil {
    /**
     * 在目标字符串的左侧，使用指定的字符，填充指定的位数
     * 如：placeholder:#; str:1; bitNum:8. 那么调用此函数后，返回的结果是#######1
     *
     * @param placeholder 填充的字符
     * @param str 被填充的字符串
     * @param bitNum 填充长度
     * @return
     */
    public static String fillAfter(String placeholder, String str, int bitNum) {
        if (str.length() < bitNum) {
            StringBuffer buffer = new StringBuffer();
            for (int index = str.length(); index < bitNum; index++) {
                buffer.append(placeholder);
            }
            buffer.append(str);
            return buffer.toString();
        }
        return str;
    }
}
