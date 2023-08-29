package com.tomato.common.util;

/**
 * 版本号,避免了修改类后对序列化兼容性的影响
 *
 * @author lizhifu
 * @since 2023/8/29
 */
public class TomatoSerialVersion {
	/**
	 * 主版本号
	 */
	private static final int MAJOR = 1;

	/**
	 * 次版本号
	 */
	private static final int MINOR = 0;

	/**
	 * 修订版本号
	 */
	private static final int PATCH = 0;

	/**
	 * 全局序列化值
	 */
	public static final long SERIAL_VERSION_UID = getVersion().hashCode();

	public static String getVersion() {
		return MAJOR + "." + MINOR + "." + PATCH;
	}
}
