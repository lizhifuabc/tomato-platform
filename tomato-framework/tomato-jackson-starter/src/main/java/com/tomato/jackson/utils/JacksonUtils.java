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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * json util 区别 阿里的fastjson
 * @author lizhifu
 */
public class JacksonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JacksonUtils.class);
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
        logger.info("JacksonUtils init success!");
    }

    public static ObjectMapper objectMapper() {
        return OBJECT_MAPPER;
    }

    public static ObjectMapper registerModule(Module module) {
        return objectMapper().registerModules(module);
    }

    public static <T> String toJson(T domain) {
        try {
            return objectMapper().writeValueAsString(domain);
        } catch (JsonProcessingException e) {
            logger.error("Jackson json processing error, when to json! {}", e.getMessage());
            return null;
        }
    }

    public static TypeFactory getTypeFactory() {
        return objectMapper().getTypeFactory();
    }

    public static <T> T toObject(String content, Class<T> valueType) {
        try {
            return objectMapper().readValue(content, valueType);
        } catch (JsonProcessingException e) {
            logger.error("Jackson json processing error, when to object with value type! {}", e.getMessage());
            return null;
        }
    }

    public static <T> T toObject(String content, TypeReference<T> typeReference) {
        try {
            return objectMapper().readValue(content, typeReference);
        } catch (JsonProcessingException e) {
            logger.error("Jackson json processing error, when to object with type reference! {}", e.getMessage());
            return null;
        }
    }

    public static <T> T toObject(String content, JavaType javaType) {
        try {
            return objectMapper().readValue(content, javaType);
        } catch (JsonProcessingException e) {
            logger.error("Jackson json processing error, when to object with java type! {}", e.getMessage());
            return null;
        }
    }

    public static <T> List<T> toList(String content, Class<T> clazz) {
        JavaType javaType = objectMapper().getTypeFactory().constructParametricType(List.class, clazz);
        return toObject(content, javaType);
    }

    public static <K, V> Map<K, V> toMap(String content, Class<K> keyClass, Class<V> valueClass) {
        JavaType javaType = objectMapper().getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
        return toObject(content, javaType);
    }

    public static <T> Set<T> toSet(String content, Class<T> clazz) {
        JavaType javaType = getTypeFactory().constructCollectionLikeType(Set.class, clazz);
        return toObject(content, javaType);
    }

    public static <T> T[] toArray(String content, Class<T> clazz) {
        JavaType javaType = getTypeFactory().constructArrayType(clazz);
        return toObject(content, javaType);
    }

    public static <T> T[] toArray(String content) {
        return toObject(content, new TypeReference<T[]>() {
        });
    }

    public static JsonNode toNode(String content) {
        try {
            return objectMapper().readTree(content);
        } catch (JsonProcessingException e) {
            logger.error("Jackson json processing error, when to node with string! {}", e.getMessage());
            return null;
        }
    }

    public static JsonNode toNode(JsonParser jsonParser) {
        try {
            return objectMapper().readTree(jsonParser);
        } catch (IOException e) {
            logger.error("Jackson io error, when to node with json parser! {}", e.getMessage());
            return null;
        }
    }

    public static JsonParser createParser(String content) {
        try {
            return objectMapper().createParser(content);
        } catch (IOException e) {
            logger.error("Jackson io error, when create parser! {}", e.getMessage());
            return null;
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
