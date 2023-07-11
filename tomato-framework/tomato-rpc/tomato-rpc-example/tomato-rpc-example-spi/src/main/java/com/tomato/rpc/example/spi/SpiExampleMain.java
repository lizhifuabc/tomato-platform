package com.tomato.rpc.example.spi;

import com.tomato.rpc.spi.extension.ExtensionLoader;

/**
 * 示例
 *
 * @author lizhifu
 * @since 2023/7/11
 */
public class SpiExampleMain {
    public static void main(String[] args) {
        SpiService spiService = ExtensionLoader.getExtension(SpiService.class, "spiService");
        String result = spiService.hello("你好");
        System.out.println(result);
    }
}
