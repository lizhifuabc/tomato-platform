package com.tomato.id.generator.impl;

import com.tomato.id.generator.IdGenerator;

/**
 * id 生成
 *
 * @author lizhifu
 * @since 2022/12/15
 */
public class DefaultIdGenerator implements IdGenerator {
    private final Sequence sequence;

    public DefaultIdGenerator() {
        this.sequence = new Sequence();
    }
    @Override
    public Long nextId() {
        return sequence.nextId();
    }
}
