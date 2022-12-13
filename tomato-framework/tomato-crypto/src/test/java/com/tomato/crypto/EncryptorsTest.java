package com.tomato.crypto;

import org.springframework.security.crypto.encrypt.AesBytesEncryptor;

import java.util.Arrays;

/**
 * TODO
 *
 * @author lizhifu
 * @date 2022/12/13
 */
public class EncryptorsTest {
    public static void main(String[] args) {
        AesBytesEncryptor aesBytesEncryptor = new AesBytesEncryptor("123456","123456");
        System.out.println(Arrays.toString(aesBytesEncryptor.encrypt("aesBytesEncryptor".getBytes())));
    }
}
