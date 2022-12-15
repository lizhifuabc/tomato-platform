package com.tomato.util.crypto;

import com.tomato.util.lang.UUIDGenerator;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * AES CBC 模式加密工具类
 *
 * @author lizhifu
 * @since 2022/12/9
 */
public class AESUtils {
    /**
     * AES
     */
    private static final String AES = "AES";
    /**
     * AES 算法
     */
    private static final String AES_CBC_CIPHER = "AES/CBC/PKCS5Padding";

    /**
     * 加密
     *
     * @param data 需要加密的内容
     * @param key  加密密码
     * @return
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception{
        SecretKeySpec secretKey = new SecretKeySpec(key, AES);
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, AES);
        Cipher cipher = Cipher.getInstance(AES_CBC_CIPHER);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(key));
        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     * @param data 待解密内容
     * @param key  解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception{
        SecretKeySpec secretKey = new SecretKeySpec(key, AES);
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, AES);
        Cipher cipher = Cipher.getInstance(AES_CBC_CIPHER);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(key));
        return cipher.doFinal(data);
    }

    /**
     * 加密
     *
     * @param data 需要加密的内容
     * @param key  加密密码
     * @return
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] valueByte = encrypt(data.getBytes(StandardCharsets.UTF_8), key.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(valueByte);
    }

    /**
     * 解密
     *
     * @param data 待解密内容 base64 字符串
     * @param key  解密密钥
     * @return
     */
    public static String decrypt(String data, String key) throws Exception {
        byte[] originalData = Base64.getDecoder().decode(data.getBytes());
        byte[] valueByte = decrypt(originalData, key.getBytes(StandardCharsets.UTF_8));
        return new String(valueByte);
    }

    /**
     * 生成一个随机字符串密钥
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String generateRandomKey() {
        return UUIDGenerator.get32UUID().substring(0, 16);
    }
}
