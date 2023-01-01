package com.tomato.id.generator.impl;

import com.tomato.id.generator.IdGenerator;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * UUID 生成
 *
 * @author lizhifu
 * @since 2022/12/30
 */
public class UUIDGenerator implements IdGenerator {
    @Override
    public String nextId() {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        return new UUID(threadLocalRandom.nextLong(), threadLocalRandom.nextLong()).toString().replace("-", "");
    }

    public static void main(String[] args) {

    }
}
