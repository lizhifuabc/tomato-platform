package com.tomato.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO bug
 * Reflect util.<br>
 * Refer to cn.hutool.core.util.ReflectUtil:<br>
 * {@link this#getFieldsDirectly(Class, boolean)} <br>
 * {@link this#setFieldValue(Object, Field, Object)} <br>
 * {@link this#getDefaultValue(Class)} <br>
 */
public class ReflectUtil {

    private static final Map<Class<?>, Field[]> FIELDS_CACHE = new ConcurrentHashMap<>();

    public static Object getFieldValue(Object obj, String fieldName) {
        Field field = getField(obj instanceof Class ? (Class<?>) obj : obj.getClass(), fieldName);
        return getFieldValue(obj, field);
    }

    public static Object getFieldValue(Object obj, Field field) {
        if (null == field) {
            return null;
        }
        if (obj instanceof Class) {
            obj = null;
        }
        setAccessible(field);
        Object result;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            String exceptionMsg = String.format("IllegalAccess for %s.%s", field.getDeclaringClass(), field.getName());
            throw new RuntimeException(exceptionMsg, e);
        }
        return result;
    }

    public static <T extends AccessibleObject> T setAccessible(T accessibleObject) {
        if (null != accessibleObject && !accessibleObject.isAccessible()) {
            accessibleObject.setAccessible(true);
        }
        return accessibleObject;
    }

    public static Field getField(Class<?> beanClass, String name) throws SecurityException {
        final Field[] fields = getFields(beanClass);
        for (int i = 0; i < fields.length; i++) {
            if(name.equals(fields[i].getName())){
                return fields[i];
            }
        }
        return null;
//        return Arrays.stream(fields).filter(field -> name.equals(getFieldName(field))).findFirst().get();
    }

    public static Field[] getFields(Class<?> beanClass) throws SecurityException {
        Field[] allFields = FIELDS_CACHE.get(beanClass);
        if (null != allFields) {
            return allFields;
        }
        allFields = getFieldsDirectly(beanClass, true);
        FIELDS_CACHE.put(beanClass, allFields);
        return allFields;
    }

    public static Field[] getFieldsDirectly(Class<?> beanClass, boolean withSuperClassFields) throws SecurityException {
        Field[] allFields = null;
        Class<?> searchType = beanClass;
        Field[] declaredFields;
        while (searchType != null) {
            declaredFields = searchType.getDeclaredFields();
            if (null == allFields) {
                allFields = declaredFields;
            } else {
                int length = allFields.length;
                allFields = Arrays.copyOf(allFields, length + declaredFields.length);
                System.arraycopy(declaredFields, 0, allFields, length, declaredFields.length);
            }
            searchType = withSuperClassFields ? searchType.getSuperclass() : null;
        }

        return allFields;
    }
    public static void setFieldValue(Object obj, String fieldName, Object value) throws RuntimeException {
        final Field field = getField((obj instanceof Class) ? (Class<?>) obj : obj.getClass(), fieldName);
        setFieldValue(obj, field, value);
    }

    public static void setFieldValue(Object obj, Field field, Object value) throws RuntimeException {
        final Class<?> fieldType = field.getType();
        if (null != value) {
            if (!fieldType.isAssignableFrom(value.getClass())) {
                final Object targetValue = cast(fieldType, value);
                if (null != targetValue) {
                    value = targetValue;
                }
            }
        } else {
            value = getDefaultValue(fieldType);
        }
        setAccessible(field);
        try {
            field.set(obj instanceof Class ? null : obj, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("IllegalAccess for " + obj + "." + field.getName(), e);
        }
    }

    /**
     * find the method associated with the method name<br>
     * if find multiple, return the first, parameter is equivocal
     *
     * @param clazz      the class
     * @param methodName retrieves the method name
     * @return find method
     */
    public static Method getMethodByName(Class<?> clazz, String methodName) {
        if (Objects.nonNull(clazz) && Objects.nonNull(methodName)) {
            Method method = Arrays.stream(clazz.getMethods())
                    .filter(m -> Objects.equals(m.getName(), methodName))
                    .findFirst().orElse(null);
            if (method != null) {
                return method;
            }
            return Arrays.stream(clazz.getDeclaredMethods())
                    .filter(m -> Objects.equals(m.getName(), methodName))
                    .findFirst().orElse(null);
        }
        return null;
    }

    /**
     * find the method associated with the method name
     *
     * @param clazz      the class
     * @param methodName retrieves the method name
     * @param arguments  matched parameters class
     * @return find method
     */
    public static Method getMethodByName(Class<?> clazz, String methodName, Class<?>... arguments) {
        try {
            if (Objects.nonNull(clazz) && Objects.nonNull(methodName)) {
                return clazz.getMethod(methodName, arguments);
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Cast the value to the type <br>
     * If a ClassCastException occurs, return null
     *
     * @param clazz Cast class
     * @param value The cast value
     * @return The value after the cast is completed
     */
    public static Object cast(Class<?> clazz, Object value) {
        try {
            return clazz.cast(value);
        } catch (ClassCastException e) {
            return null;
        }
    }

    /**
     * the default value is obtained if it is a primitive type, and NULL if it is not
     *
     * @param clazz clazz
     * @return default value
     */
    public static Object getDefaultValue(Class<?> clazz) {
        if (Objects.isNull(clazz) || !clazz.isPrimitive()) {
            return null;
        }
        if (long.class.isAssignableFrom(clazz)) {
            return 0L;
        } else if (int.class.isAssignableFrom(clazz)) {
            return 0;
        } else if (short.class.isAssignableFrom(clazz)) {
            return (short) 0;
        } else if (char.class.isAssignableFrom(clazz)) {
            return (char) 0;
        } else if (byte.class.isAssignableFrom(clazz)) {
            return (byte) 0;
        } else if (double.class.isAssignableFrom(clazz)) {
            return 0D;
        } else if (float.class.isAssignableFrom(clazz)) {
            return 0f;
        } else if (boolean.class.isAssignableFrom(clazz)) {
            return false;
        }
        return null;
    }

    /**
     * invoke
     *
     * @param obj       the obj
     * @param method    the method
     * @param arguments parameters
     * @return result for zhe method
     */
    @SuppressWarnings("unchecked")
    public static <T> T invoke(Object obj, Method method, Object... arguments) {
        try {
            return (T) method.invoke(obj, arguments);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * get instance
     *
     * @param cls the class
     * @return new Instance
     */
    public static Object createInstance(Class<?> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
