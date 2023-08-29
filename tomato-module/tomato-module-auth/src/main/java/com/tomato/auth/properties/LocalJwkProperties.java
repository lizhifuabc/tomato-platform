package com.tomato.auth.properties;

import lombok.Data;

/**
 * 本地jwk配置
 *
 * @author lizhifu
 */
@Data
public class LocalJwkProperties {

	/**
	 * jks证书文件路径
	 */
	private String jksKeyStore = "classpath*:security/tomato-auth.jks";

	/**
	 * jks证书密码
	 */
	private String jksKeyPassword = "tomato-auth";

	/**
	 * jks证书密钥库密码
	 */
	private String jksStorePassword = "tomato-auth";

	/**
	 * jks证书别名
	 */
	private String jksKeyAlias = "tomato-auth";

}
