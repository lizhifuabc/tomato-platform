package com.tomato.util;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;

/**
 * 枚举工具类
 *
 * @author lizhifu
 */
public class EnumUtil {

	/**
	 * 指定类是否为Enum类
	 *
	 * @param clazz 类
	 * @return 是否为Enum类
	 */
	public static boolean isEnum(Class<?> clazz) {
		return clazz.isEnum();
	}

	/**
	 * 指定类是否为Enum类
	 *
	 * @param obj 类
	 * @return 是否为Enum类
	 */
	public static boolean isEnum(Object obj) {
		return obj.getClass().isEnum();
	}

	/**
	 * Enum对象转String，调用{@link Enum#name()} 方法
	 *
	 * @param e Enum
	 * @return name值
	 * @since 4.1.13
	 */
	public static String toString(Enum<?> e) {
		return null != e ? e.name() : null;
	}

	/**
	 * 获取给定位置的枚举值
	 *
	 * @param <E>       枚举类型泛型
	 * @param enumClass 枚举类
	 * @param index     枚举索引
	 * @return 枚举值，null表示无此对应枚举
	 * @since 5.1.6
	 */
	public static <E extends Enum<E>> E getEnumAt(Class<E> enumClass, int index) {
		final E[] enumConstants = enumClass.getEnumConstants();
		return index >= 0 && index < enumConstants.length ? enumConstants[index] : null;
	}

	/**
	 * 字符串转枚举，调用{@link Enum#valueOf(Class, String)}
	 *
	 * @param <E>       枚举类型泛型
	 * @param enumClass 枚举类
	 * @param value     值
	 * @return 枚举值
	 */
	public static <E extends Enum<E>> E fromString(Class<E> enumClass, String value) {
		return Enum.valueOf(enumClass, value);
	}

	/**
	 * 枚举类中所有枚举对象的name列表
	 *
	 * @param clazz 枚举类
	 * @return name列表
	 */
	public static List<String> getNames(Class<? extends Enum<?>> clazz) {
		final Enum<?>[] enums = clazz.getEnumConstants();
		if (null == enums) {
			return null;
		}
		final List<String> list = new ArrayList<>(enums.length);
		for (Enum<?> e : enums) {
			list.add(e.name());
		}
		return list;
	}

	/**
	 * 获得枚举类中各枚举对象下指定字段的值
	 *
	 * @param clazz     枚举类
	 * @param fieldName 字段名，最终调用getXXX方法
	 * @return 字段值列表
	 */
	public static List<Object> getFieldValues(Class<? extends Enum<?>> clazz, String fieldName) {
		final Enum<?>[] enums = clazz.getEnumConstants();
		if (null == enums) {
			return null;
		}
		final List<Object> list = new ArrayList<>(enums.length);
		for (Enum<?> e : enums) {
			list.add(ReflectUtil.getFieldValue(e, fieldName));
		}
		return list;
	}

	/**
	 * 获得枚举类中所有的字段名<br>
	 * 除用户自定义的字段名，也包括“name”字段，例如：
	 *
	 * <pre>
	 *   EnumUtil.getFieldNames(Color.class) == ["name", "index"]
	 * </pre>
	 *
	 * @param clazz 枚举类
	 * @return 字段名列表
	 */
	public static List<String> getFieldNames(Class<? extends Enum<?>> clazz) {
		final List<String> names = new ArrayList<>();
		final Field[] fields = ReflectUtil.getFields(clazz);
		String name;
		for (Field field : fields) {
			name = field.getName();
			if (field.getType().isEnum() || name.contains("$VALUES") || "ordinal".equals(name)) {
				continue;
			}
			if (false == names.contains(name)) {
				names.add(name);
			}
		}
		return names;
	}

	/**
	 * 通过 某字段对应值 获取 枚举，获取不到时为 {@code null}
	 *
	 * @param enumClass 枚举类
	 * @param predicate 条件
	 * @param <E>       枚举类型
	 * @return 对应枚举 ，获取不到时为 {@code null}
	 */
	public static <E extends Enum<E>> E getBy(Class<E> enumClass, Predicate<? super E> predicate) {
		return Arrays.stream(enumClass.getEnumConstants())
				.filter(predicate).findFirst().orElse(null);
	}

	/**
	 * 获取枚举字符串值和枚举对象的Map对应，使用LinkedHashMap保证有序<br>
	 * 结果中键为枚举名，值为枚举对象
	 *
	 * @param <E>       枚举类型
	 * @param enumClass 枚举类
	 * @return 枚举字符串值和枚举对象的Map对应，使用LinkedHashMap保证有序
	 * @since 4.0.2
	 */
	public static <E extends Enum<E>> LinkedHashMap<String, E> getEnumMap(final Class<E> enumClass) {
		final LinkedHashMap<String, E> map = new LinkedHashMap<>();
		for (final E e : enumClass.getEnumConstants()) {
			map.put(e.name(), e);
		}
		return map;
	}
	/**
	 * 判断某个值是存在枚举中
	 *
	 * @param <E>       枚举类型
	 * @param enumClass 枚举类
	 * @param val       需要查找的值
	 * @return 是否存在
	 */
	public static <E extends Enum<E>> boolean contains(final Class<E> enumClass, String val) {
		return EnumUtil.getEnumMap(enumClass).containsKey(val);
	}

	/**
	 * 判断某个值是不存在枚举中
	 *
	 * @param <E>       枚举类型
	 * @param enumClass 枚举类
	 * @param val       需要查找的值
	 * @return 是否不存在
	 */
	public static <E extends Enum<E>> boolean notContains(final Class<E> enumClass, String val) {
		return false == contains(enumClass, val);
	}
}
