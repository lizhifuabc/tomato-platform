package com.tomato.gen.service.velocity.impl;

import com.tomato.gen.config.GenConfig;
import com.tomato.gen.constant.TemplateConstant;
import com.tomato.gen.domain.bo.TableBo;
import com.tomato.gen.domain.entity.GenFieldTypeEntity;
import com.tomato.gen.service.GenFieldTypeService;
import com.tomato.gen.service.velocity.VelocityService;
import org.apache.velocity.VelocityContext;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;

/**
 * java mapper xml 模板
 *
 * @author lizhifu
 * @since 2023/3/29
 */
@Component
public class MapperXmlVelocityServiceImpl implements VelocityService {

	private final GenConfig genConfig;

	private final GenFieldTypeService genFieldTypeService;

	public MapperXmlVelocityServiceImpl(GenConfig genConfig, GenFieldTypeService genFieldTypeService) {
		this.genConfig = genConfig;
		this.genFieldTypeService = genFieldTypeService;
	}

	@Override
	public String getTemplate() {
		return TemplateConstant.VM_MAPPER_XML;
	}

	@Override
	public VelocityContext render(TableBo tableBo) {
		HashSet<String> importPackageList = new HashSet<>();
		tableBo.getTableColumnList().forEach(tableColumn -> {
			// 列的数据类型，转换成Java类型
			GenFieldTypeEntity dataType = genFieldTypeService.getByDataType(tableColumn.getDataType());
			Optional.ofNullable(dataType.getPackageName()).ifPresent(importPackageList::add);
			tableColumn.setJavaType(dataType.getAttrType());
		});

		VelocityContext context = new VelocityContext();
		context.put("table", tableBo.getTable());
		context.put("tableColumnList", tableBo.getTableColumnList());
		context.put("genConfig", genConfig);
		context.put("importPackageList", importPackageList);
		return context;
	}

}
