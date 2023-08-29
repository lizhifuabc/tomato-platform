package com.tomato.doc.hash;

import com.google.common.hash.Funnels;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

/**
 * HashTest
 *
 * @author lizhifu
 * @since 2023/4/14
 */
public class HashTest {

	public static void main(String[] args) {
		String key = "tomato";
		HashCode hashCode = Hashing.murmur3_128().hashObject(key, Funnels.stringFunnel(StandardCharsets.UTF_8));
		System.out.println(hashCode.bits());
	}

}
