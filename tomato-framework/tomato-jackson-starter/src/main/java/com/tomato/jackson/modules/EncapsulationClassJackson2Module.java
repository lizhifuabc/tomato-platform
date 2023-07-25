package com.tomato.jackson.modules;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 修改Long类型的序列化方式
 *
 * @author lizhifu
 * @since 2023/7/25
 */
public class EncapsulationClassJackson2Module extends SimpleModule {
    public EncapsulationClassJackson2Module() {
        this.addSerializer(Long.class, ToStringSerializer.instance);
        this.addSerializer(Long.TYPE, ToStringSerializer.instance);
    }
}
