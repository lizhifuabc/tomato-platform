package com.tomato.jackson;

/**
 * Pattern
 *
 * @author lizhifu
 * @date 2022/12/12
 */
public class PatternTest {
    public static void main(String[] args) {
        Long no = 18810869700L;
        System.out.println(no.toString().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
    }
}
