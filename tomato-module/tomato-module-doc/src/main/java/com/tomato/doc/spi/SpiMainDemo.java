package com.tomato.doc.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * spi demo
 *
 * @author lizhifu
 * @since 2023/3/16
 */
public class SpiMainDemo {
    public static void main(String[] args) {
        ServiceLoader<SpiDemo> spiDemos = ServiceLoader.load(SpiDemo.class);
        Iterator<SpiDemo> iterator = spiDemos.iterator();
        while (iterator.hasNext()){
            SpiDemo spiDemo = iterator.next();
            spiDemo.hello();
        }
    }
}
