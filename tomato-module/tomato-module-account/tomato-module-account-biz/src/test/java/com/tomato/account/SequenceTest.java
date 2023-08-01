package com.tomato.account;

import com.tomato.id.generator.impl.Sequence;

/**
 * Sequence
 *
 * @author lizhifu
 * @since 2023/1/1
 */
public class SequenceTest {
    private static Sequence sequence = new Sequence();
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Long id = sequence.nextId();
            System.out.println(sequence.getGenerateDateTime(id));
        }
    }
}
