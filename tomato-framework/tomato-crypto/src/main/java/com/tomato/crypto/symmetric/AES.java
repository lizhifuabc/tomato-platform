package com.tomato.crypto.symmetric;

import javax.crypto.SecretKey;
import java.security.spec.AlgorithmParameterSpec;

/**
 * AES加密算法实现<br>
 *
 * @author lizhifu
 * @date 2022/12/12
 */
public class AES {

	/**
	 * 构造
	 * @param mode 模式
	 * @param padding 补码方式
	 * @param key 密钥，支持三种密钥长度：128、192、256位
	 * @param paramsSpec 算法参数，例如加盐等
	 */
	public AES(String mode, String padding, SecretKey key, AlgorithmParameterSpec paramsSpec) {
	}

}
