package com.tomato.jackson.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tomato.jackson.modules.EncapsulationClassJackson2Module;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义Jackson2ObjectMapperBuilderCustomizer
 *
 * @author lizhifu
 * @since 2023/7/25
 */
public class CustomizerJackson2ObjectMapperBuilderCustomizer implements Ordered, Jackson2ObjectMapperBuilderCustomizer {
    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
        // 要启用的功能
        builder.featuresToEnable(
                //  按键值顺序排序map entries
                SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS,
                // 允许单引号
                JsonParser.Feature.ALLOW_SINGLE_QUOTES,
                // 允许非转义控制符
                JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature()
        );
        // 要禁用的功能
        builder.featuresToDisable(
                // 禁止序列化空对象报错
                SerializationFeature.FAIL_ON_EMPTY_BEANS,
                // 不将日期作为时间戳格式输出
                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                //  忽略未知属性,不报错
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
        );

        builder.modulesToInstall(
                modules -> {
                    List<Module> install = new ArrayList<>(modules);
                    install.add(new EncapsulationClassJackson2Module());
                    // 支持JDK8新特性
                    install.add(new Jdk8Module());
                    // 支持新的Java日期时间API
                    install.add(new JavaTimeModule());
                    builder.modulesToInstall(toArray(install));
                }
        );
        builder.findModulesViaServiceLoader(true);
    }

    @Override
    public int getOrder() {
        return 1;
    }

    private Module[] toArray(List<Module> modules) {
        if (!modules.isEmpty()) {
            Module[] temps = new Module[modules.size()];
            return modules.toArray(temps);
        } else {
            return new Module[] {};
        }
    }
}
