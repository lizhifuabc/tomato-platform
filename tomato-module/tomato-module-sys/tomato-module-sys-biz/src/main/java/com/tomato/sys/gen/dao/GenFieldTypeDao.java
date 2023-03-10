package com.tomato.sys.gen.dao;

import com.tomato.sys.domain.entity.GenFieldTypeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 字段类型管理
 *
 * @author lizhifu
 * @since 2023/1/17
 */
@Mapper
public interface GenFieldTypeDao {
    /**
     * 查询所有
     * @return
     */
    List<GenFieldTypeEntity> selectList();
}
