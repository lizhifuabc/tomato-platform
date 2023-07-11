package com.tomato.rpc.spi.extension;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * spi 扩展加载器，实现SPI机制最核心的类
 *
 * @author lizhifu
 * @since 2023/7/11
 */
public final class ExtensionLoader<T> {
    private static final Logger LOG = LoggerFactory.getLogger(ExtensionLoader.class);
    /**
     * 缓存的拓展实现类集合（Holder是拓展对象的包装实现类，在其中包装了实现类）
     */
    private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();
    /**
     * 缓存的扩展对象集合，key为扩展名，value为扩展对象
     */
    private final ConcurrentMap<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>();
    /**
     * 扩展类的实例缓存
     */
    private final Map<Class<?>, Object> SPI_CLASS_INSTANCES = new ConcurrentHashMap<>();
    /**
     * 缓存某个接⼝类型所对应的 ExtensionLoader实例
     * key为拓展接口，value为对应的加载器
     */
    private static final ConcurrentHashMap<Class<?>, ExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap<>();
    /**
     * 扩展类的路径，接口完整名称命名的文件所在的目录
     * 该文件中以 xxx=全限定名Xxx 实现类标示该接口的所有可能实现类
     * impl1=com.alibaba.dubbo.common.extensionloader.ext1.impl.SimpleExtImpl1
     * impl2=com.alibaba.dubbo.common.extensionloader.ext1.impl.SimpleExtImpl2
     * impl3=com.alibaba.dubbo.common.extensionloader.ext1.impl.SimpleExtImpl3
     */
    private static final String SERVICES_DIRECTORY = "META-INF/services/";
    /**
     * 扩展类的路径集合
     */
    private static final String[] SPI_DIRECTORIES = new String[]{
            SERVICES_DIRECTORY,
    };
    /**
     * 被拓展的接口对象
     */
    private final Class<T> service;
    /**
     * 类加载器
     */
    private final ClassLoader classLoader;

    /**
     * 私有构造函数，不允许外部调用
     * @param service 扩展类的class
     * @param classLoader 类加载器
     */
    private ExtensionLoader(final Class<T> service,final ClassLoader classLoader) {
        this.service = service;
        this.classLoader = classLoader;
    }
    /**
     * 获取接口实现类实例
     * <p>1. 获取类扩展加载器
     * <p>2. 通过类扩展加载器获取接口实现类实例
     *
     * @param service 接口的Class
     * @param name SPI名称
     * @param <T> 泛型类型
     * @return 泛型实例
     */
    public static <T> T getExtension(final Class<T> service, String name){
        return getExtensionLoader(service).getSpiClassInstance(name);
    }
    /**
     * 获取类扩展加载器
     *
     * @param <T>   泛型类型
     * @param service 接口的Class实例
     * @return 泛型实例
     */
    public static <T> ExtensionLoader<T> getExtensionLoader(final Class<T> service) {
        return getExtensionLoader(service, ExtensionLoader.class.getClassLoader());
    }
    /**
     * 获取/创建扩展类的加载器
     * <p>1. 是否有扩展类加载器，没有就创建一个
     * @param service 扩展类的class
     * @param cl 类加载器
     * @return 扩展类的实现类
     * @param <T> 泛型类型
     */
    public static <T> ExtensionLoader<T> getExtensionLoader(final Class<T> service, final ClassLoader cl) {
        Objects.requireNonNull(service, "扩展类的class不能为空");
        if (!service.isInterface()) {
            throw new IllegalArgumentException("扩展类 (" + service + ") 不是接口!");
        }
        ExtensionLoader<T> extensionLoader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(service);
        if (Objects.nonNull(extensionLoader)) {
            return extensionLoader;
        }
        EXTENSION_LOADERS.putIfAbsent(service, new ExtensionLoader<>(service, cl));
        return (ExtensionLoader<T>) EXTENSION_LOADERS.get(service);
    }
    /**
     * 获取扩展类的class实例
     *
     * @param name 实现类的名称
     * @return spi 类的实例
     */
    public T getSpiClassInstance(final String name) {
        Holder<Object> objectHolder = cachedInstances.get(name);
        // 不存在则创建,并放入缓存
        if (Objects.isNull(objectHolder)) {
            cachedInstances.putIfAbsent(name, new Holder<>());
            objectHolder = cachedInstances.get(name);
        }
        Object value = objectHolder.getValue();
        if (Objects.isNull(value)) {
            synchronized (cachedInstances) {
                value = objectHolder.getValue();
                if (Objects.isNull(value)) {
                    value = createExtension(name);
                    objectHolder.setValue(value);
                }
            }
        }
        return (T) value;
    }

    /**
     * 获取扩展类的class实例
     * @param name 扩展类实现类的名称
     * @return 扩展类的实例
     */
    private T createExtension(final String name) {
        Class<?> aClass = getExtensionClasses().get(name);
        if (Objects.isNull(aClass)) {
            throw new IllegalArgumentException("获取拓展实现类失败，没有找到该实现类，name:" + name + ",class:" + service.getName());
        }
        Object instance = SPI_CLASS_INSTANCES.get(aClass);
        if (Objects.isNull(instance)) {
            try {
                SPI_CLASS_INSTANCES.putIfAbsent(aClass, aClass.newInstance());
                instance = SPI_CLASS_INSTANCES.get(aClass);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalStateException("扩展实例(name: " + name + ", class: "
                        + aClass + ")  无法实例化: " + e.getMessage(), e);

            }
        }
        return (T) instance;
    }
    /**
     * 获取扩展类
     * @return 扩展类的实现类合集
     */
    public Map<String, Class<?>> getExtensionClasses() {
        // 从缓存中获取扩展类的实现类
        Map<String, Class<?>> classes = cachedClasses.getValue();
        // 双重检查
        if (Objects.isNull(classes)) {
            // 保证线程安全
            synchronized (cachedClasses) {
                // 双重检查，防止多线程下重复加载
                classes = cachedClasses.getValue();
                if (Objects.isNull(classes)) {
                    classes = loadExtensionClass();
                    cachedClasses.setValue(classes);
                }
            }
        }
        return classes;
    }
    /**
     * 加载扩展类
     * @return 扩展类的实现类合集
     */
    private Map<String, Class<?>> loadExtensionClass() {
        return loadDirectory();
    }
    /**
     * 获取扩展类的实现类
     * @return 扩展类的实现类
     */
    private Map<String, Class<?>> loadDirectory() {
        // 存储扩展类的实现类
        Map<String, Class<?>> classes = new HashMap<>(16);
        for (String directory : SPI_DIRECTORIES){
            String fileName = directory + service.getName();
            try {
                // 读取所有 jar 包里面 META-INF/services 包下面的文件
                Enumeration<URL> urls = Objects.nonNull(this.classLoader) ? classLoader.getResources(fileName) : ClassLoader.getSystemResources(fileName);
                if (Objects.nonNull(urls)) {
                    // 挨个遍历取到的文件
                    while (urls.hasMoreElements()) {
                        URL url = urls.nextElement();
                        loadResources(classes, url);
                    }
                }
            } catch (IOException t) {
                LOG.error("加载扩展类错误 {}", fileName, t);
            }
        }
        return classes;
    }
    /**
     * 加载扩展类
     * @param cachedClasses 扩展类的缓存
     * @param url 扩展类的路径
     */
    private void loadResources(final Map<String, Class<?>> cachedClasses, final URL url) throws IOException {
        InputStream inputStream = url.openStream();
        Properties properties = new Properties();
        properties.load(inputStream);
        properties.forEach((k, v) -> {
            String name = (String) k;
            String classPath = (String) v;
            try {
                loadClass(cachedClasses, name, classPath);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("加载扩展资源错误", e);
            }
        });
    }
    /**
     * 加载指定的类,并进行校验
     * @param cachedClasses 扩展类的缓存
     * @param name 扩展类的名称
     * @param classPath 扩展类的路径
     * @throws ClassNotFoundException 类不存在
     */
    private void loadClass(final Map<String, Class<?>> cachedClasses,final String name, final String classPath) throws ClassNotFoundException {
        // 通过反射拿到实现类的实例
        Class<?> subClass = Objects.nonNull(this.classLoader) ? Class.forName(classPath, true, this.classLoader) : Class.forName(classPath);
        // 如果声明的接口跟这个具体的实现类是属于同一类型，（可以理解为Java的一种多态，接口跟实现类、父类和子类等等这种关系。）则构造实例
        if (!service.isAssignableFrom(subClass)) {
            throw new IllegalStateException("加载扩展类失败," + subClass + " 类型不是 " + service);
        }
        // 判断是否已经加载,如果已经加载,则抛出异常，不允许重复加载；不存在则添加到缓存中
        Optional.ofNullable(cachedClasses.putIfAbsent(name, subClass)).ifPresent((oldClass) -> {
            throw new IllegalStateException("加载扩展类失败,重复加载的类 " + service.getName() + " 名称 " + name + " 已经存在的类名称 " + oldClass.getName() + " 等待加入的类名称 " + subClass.getName());
        });
    }

    /**
     * holder 类 用于存储扩展类
     * @param <T>
     */
    public static class Holder<T> {

        private volatile T value;

        public T getValue() {
            return value;
        }

        public void setValue(final T value) {
            this.value = value;
        }
    }
}
