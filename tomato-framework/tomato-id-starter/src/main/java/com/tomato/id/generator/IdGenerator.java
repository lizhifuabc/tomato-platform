package com.tomato.id.generator;

/**
 * id 接口
 *
 * @author lizhifu
 * @date 2022/12/15
 */
public interface IdGenerator {
    /**
     * 生成Id
     *
     * @return id
     */
    Number nextId();
}
