package com.tomato.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.security.Security;

/**
 * BouncyCastleProvider 单例实例
 * BouncyCastle：一个开源的第三方算法提供商，提供了很多Java标准库没有提供的哈希算法和加密算法；
 * 使用第三方算法前需要通过Security.addProvider()注册。
 * {@link BouncyCastleProvider}
 * @author lizhifu
 */
public enum BouncyCastleProviderInstance {
    /**
     * 实例
     */
	INSTANCE;
	private Provider provider;

	BouncyCastleProviderInstance() {
		Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
		if(provider == null){
			provider = new BouncyCastleProvider();
            // 增加加密解密的算法提供者，默认优先使用
			Security.addProvider(provider);
		}
		this.provider = provider;
	}
	/**
	 * 获取{@link Provider}
	 *
	 * @return {@link Provider}
	 */
	public Provider getProvider() {
		return this.provider;
	}
}
