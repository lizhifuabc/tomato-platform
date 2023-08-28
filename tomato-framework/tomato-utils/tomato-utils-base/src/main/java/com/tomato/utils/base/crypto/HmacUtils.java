package com.tomato.utils.base.crypto;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * hmac 签名工具类
 *
 * @author lizhifu
 * @since 2023/7/19
 */
public class HmacUtils {
    /**
     * Base64 HmacSHA256 算法签名
     *
     * @param secret 密钥
     * @param input  加密内容
     * @return 签名
     * @throws Exception 异常
     */
    public static String encodeBase64HmacSHA256(String secret, String input) throws Exception {
        return encodeBase64Hmac("HmacSHA256", secret, input);
    }

    /**
     * Base64 MAC 算法签名
     *
     * @param algorithm MAC算法支持 HmacMD5 HmacSHA1 HmacSHA256
     * @param secret    密钥
     * @param input     加密内容
     * @return 签名
     * @throws Exception 异常
     */
    public static String encodeBase64Hmac(String algorithm, String secret, String input) throws Exception {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), algorithm));
        byte[] signData = mac.doFinal(input.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(signData);
    }
    public static void main(String[] args) throws Exception {
        String secret = "123456";
        String input = "123456";
        String hmacMD5 = HmacUtils.encodeBase64Hmac("HmacMD5", secret, input);
        String hmacSHA256 = HmacUtils.encodeBase64HmacSHA256(secret, input);
        System.out.println(hmacSHA256);
        System.out.println(hmacMD5);
    }

}
