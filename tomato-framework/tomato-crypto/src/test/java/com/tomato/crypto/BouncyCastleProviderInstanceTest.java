package com.tomato.crypto;

import java.security.Provider;

/**
 * BouncyCastleProviderInstance
 *
 * @author lizhifu
 * @date 2022/12/12
 */
public class BouncyCastleProviderInstanceTest {
    public static void main(String[] args) {
        Provider provider = BouncyCastleProviderInstance.INSTANCE.getProvider();
        System.out.println(provider);
    }
}
