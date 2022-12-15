package com.tomato.id.generator.impl;

import com.tomato.id.generator.IdGenerator;
import com.tomato.util.lang.Sequence;

import java.net.InetAddress;

/**
 * id 生成
 *
 * @author lizhifu
 * @since 2022/12/15
 */
public class DefaultIdGenerator implements IdGenerator {
    private final Sequence sequence;

    public DefaultIdGenerator() {
        this.sequence = new Sequence(null);
    }
    public DefaultIdGenerator(InetAddress inetAddress) {
        this.sequence = new Sequence(inetAddress);
    }
    @Override
    public Long nextId() {
        return sequence.nextId();
    }
}
