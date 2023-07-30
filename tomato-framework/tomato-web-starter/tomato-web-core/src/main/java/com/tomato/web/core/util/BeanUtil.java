package com.tomato.web.core.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * bean 工具类
 * <p>属性复制</p>
 * <p>属性校验</p>
 * @author lizhifu
 * @since  2022/12/13
 */
public class BeanUtil {
    /**
     * 验证器
     */
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 复制bean的属性
     *
     * @param source 源 要复制的对象
     * @param target 目标 复制到此对象
     */
    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    /**
     * 复制对象
     *
     * @param source 源 要复制的对象
     * @param target 目标 复制到此对象
     * @param <T>   目标对象类型
     * @return T
     */
    public static <T> T copy(Object source, Class<T> target) {
        if (source == null || target == null) {
            return null;
        }
        try {
            // 获取目标类的默认构造函数
            Constructor<T> constructor = target.getDeclaredConstructor();
            // 通过构造函数实例化对象
            T newInstance = constructor.newInstance();
            BeanUtils.copyProperties(source, newInstance);
            return newInstance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 复制list
     *
     * @param source 源
     * @param target 目标
     * @param <T>   源对象类型
     * @param <K>  目标对象类型
     * @return List<K>
     */
    public static <T, K> List<K> copyList(List<T> source, Class<K> target) {
        if (null == source || source.isEmpty()) {
            return Collections.emptyList();
        }
        return source.stream().map(e -> copy(e, target)).collect(Collectors.toList());
    }

    /**
     * 手动验证对象 Model的属性
     * 需要配合 hibernate-validator 校验注解
     *
     * @param t  要验证的对象
     * @return String 返回null代表验证通过，否则返回错误的信息
     */
    public static <T> String verify(T t) {
        // 获取验证结果
        Set<ConstraintViolation<T>> validate = VALIDATOR.validate(t);
        if (validate.isEmpty()) {
            // 验证通过
            return null;
        }
        // 返回错误信息
        List<String> messageList = validate.stream().map(ConstraintViolation::getMessage).toList();
        return messageList.toString();
    }
}
