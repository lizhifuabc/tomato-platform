package com.tomato.gen.service;

import com.tomato.gen.dao.GenFieldTypeDao;
import com.tomato.gen.domain.entity.GenFieldTypeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 字段类型管理
 *
 * @author lizhifu
 * @since 2023/3/29
 */
@Service
@Slf4j
public class GenFieldTypeService implements InitializingBean {

	/**
	 * 字段类型缓存
	 */
	private static final Map<String, GenFieldTypeEntity> FIELD_TYPE_ENTITY_MAP = new HashMap<>();

	private final GenFieldTypeDao genFieldTypeDao;

	public GenFieldTypeService(GenFieldTypeDao genFieldTypeDao) {
		this.genFieldTypeDao = genFieldTypeDao;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		genFieldTypeDao.selectAll().forEach(genFieldTypeEntity -> {
			// 写入缓存
			FIELD_TYPE_ENTITY_MAP.putIfAbsent(genFieldTypeEntity.getDataType(), genFieldTypeEntity);
		});
		log.info("GenFieldTypeService afterPropertiesSet");
	}

	/**
	 * 根据字段类型获取属性类型
	 * @param dataType 字段类型
	 * @return 属性类型
	 */
	public GenFieldTypeEntity getByDataType(String dataType) {
		return FIELD_TYPE_ENTITY_MAP.get(dataType);
	}

}
