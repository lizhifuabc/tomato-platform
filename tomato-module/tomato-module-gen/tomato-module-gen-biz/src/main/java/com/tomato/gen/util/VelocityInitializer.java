package com.tomato.gen.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.velocity.app.Velocity;

import java.util.Properties;

/**
 * VelocityEngine 工厂
 *
 * @author lizhifu
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE) // 私有构造函数
public class VelocityInitializer {

	/**
	 * UTF-8 字符集
	 */
	public static final String UTF_8 = "UTF-8";

	/**
	 * 初始化vm方法
	 */
	public static void initVelocity() {
		Properties p = new Properties();
		try {
			// 加载classpath目录下的vm文件
			p.setProperty("resource.loader.file.class",
					"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			// 定义字符集
			p.setProperty(Velocity.INPUT_ENCODING, UTF_8);
			// 初始化Velocity引擎，指定配置Properties
			Velocity.init(p);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
