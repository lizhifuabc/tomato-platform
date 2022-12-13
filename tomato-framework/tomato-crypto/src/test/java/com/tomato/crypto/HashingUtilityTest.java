package com.tomato.crypto;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * HashingUtility
 *
 * @author lizhifu
 * @date 2022/12/12
 */
public class HashingUtilityTest {
    public static void main(String[] args) throws Exception {
        byte[] key = null; // TODO
        byte[] input = null; // TODO
        byte[] output = null;
        SecretKeySpec keySpec = null;
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        output = cipher.doFinal(input);
        System.out.println(output);
    }
    public static String HMAC_MD5_encode(String key, String message) throws Exception {

        SecretKeySpec keySpec = new SecretKeySpec(
                key.getBytes(),
                "HmacMD5");

        Mac mac = Mac.getInstance("HmacMD5");
        mac.init(keySpec);
        byte[] rawHmac = mac.doFinal(message.getBytes());

        return "Hex.toHexString(rawHmac)";
    }
}
