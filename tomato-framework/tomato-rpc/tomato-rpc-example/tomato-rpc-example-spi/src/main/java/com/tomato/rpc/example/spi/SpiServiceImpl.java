package com.tomato.rpc.example.spi;

/**
 * SPI服务实现类
 *
 * @author lizhifu
 * @since 2023/7/11
 */
public class SpiServiceImpl implements SpiService{
    @Override
    public String hello(String name) {
        return "hello " + name;
    }
}
