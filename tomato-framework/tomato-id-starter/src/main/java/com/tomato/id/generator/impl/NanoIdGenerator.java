package com.tomato.id.generator.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.tomato.id.generator.IdGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * NanoId 生成
 *
 * @author lizhifu
 * @since 2022/12/30
 */
public class NanoIdGenerator implements IdGenerator {

	@Override
	public String nextId() {
		return NanoIdUtils.randomNanoId(ThreadLocalRandom.current(), NanoIdUtils.DEFAULT_ALPHABET,
				NanoIdUtils.DEFAULT_SIZE);
	}

	public static void main(String[] args) {
		System.out.println(NanoIdUtils.randomNanoId(ThreadLocalRandom.current(), NanoIdUtils.DEFAULT_ALPHABET,
				NanoIdUtils.DEFAULT_SIZE));
	}

}
