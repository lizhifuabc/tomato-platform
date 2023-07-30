package com.tomato.jackson.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;

/**
 * Jackson 工具类
 * @author lizhifu
 */
@Component
public class JacksonUtils {
    private static ObjectMapper OBJECT_MAPPER;
    private final ObjectMapper objectMapper;

    public JacksonUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        if (this.objectMapper == null) {
            OBJECT_MAPPER = new ObjectMapper();
            return;
        }
        OBJECT_MAPPER = this.objectMapper;
    }

    public static ObjectMapper objectMapper() {
        return OBJECT_MAPPER;
    }

    public static ObjectMapper registerModule(Module module) {
        return objectMapper().registerModules(module);
    }

    /**
     * JavaBean转换为json字符串
     * @param domain JavaBean
     * @return json字符串
     * @param <T> 泛型
     */
    public static <T> String toJson(T domain) {
        try {
            return objectMapper().writeValueAsString(domain);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static TypeFactory getTypeFactory() {
        return objectMapper().getTypeFactory();
    }

    /**
     * json字符串转换为JavaBean
     * @param content json字符串
     * @param valueType JavaBean类型
     * @return JavaBean
     * @param <T> 泛型
     */
    public static <T> T toObject(String content, Class<T> valueType) {
        if (content == null) {
            return null;
        }
        try {
            return objectMapper().readValue(content, valueType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转换为JavaBean,支持泛型
     * @param content json字符串
     * @param typeReference 泛型
     * @return JavaBean
     * @param <T> 泛型
     */
    public static <T> T toObject(String content, TypeReference<T> typeReference) {
        if (content == null) {
            return null;
        }
        try {
            return objectMapper().readValue(content, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json反序列化成对象
     * @param content json字符串
     * @param javaType JavaType
     * @return JavaBean
     * @param <T> 泛型
     */
    public static <T> T toObject(String content, JavaType javaType) {
        if (content == null) {
            return null;
        }
        try {
            return objectMapper().readValue(content, javaType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json反序列化成对象
     * @param in InputStream 输入流
     * @param javaType JavaType
     * @return JavaBean
     * @param <T> 泛型
     */
    public static <T> T toObject(InputStream in, JavaType javaType) {
        if (in == null) {
            return null;
        }
        try {
            return objectMapper().readValue(in, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json反序列化成对象
     * @param content json字符串
     * @param javaType JavaType
     * @return JavaBean
     * @param <T> 泛型
     */
    public static <T> T toObject(byte[] content, JavaType javaType) {
        if (content == null) {
            return null;
        }
        try {
            return objectMapper().readValue(content, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取集合
     * @param content json字符串
     * @param clazz 集合元素类型
     * @return 集合
     * @param <T> 泛型
     */
    public static <T> List<T> toList(String content, Class<T> clazz) {
        if (content == null) {
            return null;
        }
        JavaType javaType = objectMapper().getTypeFactory().constructParametricType(List.class, clazz);
        return toObject(content, javaType);
    }

    /**
     * 读取集合
     * @param content json字符串
     * @param keyClass key类型
     * @param valueClass value类型
     * @return 集合
     * @param <K> key泛型
     * @param <V> value泛型
     */
    public static <K, V> Map<K, V> toMap(String content, Class<K> keyClass, Class<V> valueClass) {
        if (content == null) {
            return null;
        }
        JavaType javaType = objectMapper().getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
        return toObject(content, javaType);
    }

    /**
     * 读取集合
     * @param content json字符串
     * @param clazz 集合元素类型
     * @return 集合
     * @param <T> 泛型
     */
    public static <T> Set<T> toSet(String content, Class<T> clazz) {
        if (content == null) {
            return null;
        }
        JavaType javaType = getTypeFactory().constructCollectionLikeType(Set.class, clazz);
        return toObject(content, javaType);
    }
    /**
     * 读取集合
     * @param content json字符串
     * @param clazz 集合元素类型
     * @return 集合
     * @param <T> 泛型
     */
    public static <T> T[] toArray(String content, Class<T> clazz) {
        if (content == null) {
            return null;
        }
        JavaType javaType = getTypeFactory().constructArrayType(clazz);
        return toObject(content, javaType);
    }
    /**
     * 读取集合
     * @param content json字符串
     * @return 集合
     * @param <T> 泛型
     */
    public static <T> T[] toArray(String content) {
        if (content == null) {
            return null;
        }
        return toObject(content, new TypeReference<T[]>() {
        });
    }

    /**
     * 将json字符串转换为JsonNode
     * @param content json字符串
     * @return JsonNode
     */
    public static JsonNode toNode(String content) {
        if (content == null) {
            return null;
        }
        try {
            return objectMapper().readTree(content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json字符串转换为JsonNode
     * @param jsonParser json解析器
     * @return JsonNode
     */
    public static JsonNode toNode(JsonParser jsonParser) {
        try {
            return objectMapper().readTree(jsonParser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json字符串转换为JsonNode
     * @param content json字符串
     * @return JsonNode
     */
    public static JsonParser createParser(String content) {
        if (content == null) {
            return null;
        }
        try {
            return objectMapper().createParser(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <R> R loop(JsonNode jsonNode, Function<JsonNode, R> function) {
        if (jsonNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields();
            while (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();
                loop(entry.getValue(), function);
            }
        }

        if (jsonNode.isArray()) {
            for (JsonNode node : jsonNode) {
                loop(node, function);
            }
        }

        if (jsonNode.isValueNode()) {
            return function.apply(jsonNode);
        } else {
            return null;
        }
    }
}
