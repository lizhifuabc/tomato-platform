package com.tomato.util.crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 加密工具类
 *
 * @author lizhifu
 * @since 2022年12月15日
 */
public class MD5Utils {
    /**
     * MD5
     */
    private static final String MD5 = "MD5";
    /**
     * MD5 Base64 加密
     *
     * @param str 待加密的字符串
     * @return 加密后的字符串
     */
    public static String md5Base64(String str) throws NoSuchAlgorithmException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance(MD5);
        //加密后的字符串
        byte[] src = md5.digest(str.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(src);
    }
}
