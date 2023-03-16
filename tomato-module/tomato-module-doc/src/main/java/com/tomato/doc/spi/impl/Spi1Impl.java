package com.tomato.doc.spi.impl;

import com.tomato.doc.spi.SpiDemo;

/**
 * spi demo
 *
 * @author lizhifu
 * @since 2023/3/16
 */
public class Spi1Impl implements SpiDemo {
    @Override
    public void hello() {
        System.out.println("Spi1Impl");
    }
}
