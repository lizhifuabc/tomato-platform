package com.tomato.mybatis.mapper;

import com.tomato.mybatis.mapper.mapper.*;

/**
 * 通用Mapper，实现基本功能
 * TODO 启动初始化加载 provider
 * @author lizhifu
 * @param <ID>  主键类型
 * @param <T>  实体类型
 */
public interface BaseMapper<T, ID> extends CountMapper<T, ID>,
        DeleteMapper<T, ID>,
        InsertMapper<T, ID>,
        UpdateMapper<T, ID>,
        PageMapper<T, ID>,
        SelectMapper<T, ID> {
}
