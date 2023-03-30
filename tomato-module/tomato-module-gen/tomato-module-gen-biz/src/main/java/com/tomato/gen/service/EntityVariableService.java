package com.tomato.gen.service;

import com.google.common.collect.Lists;
import com.tomato.gen.config.GenConfig;
import com.tomato.gen.dao.GenTableDao;
import com.tomato.gen.domain.resp.TableColumnResp;
import com.tomato.gen.domain.resp.TableResp;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 实体生成
 *
 * @author lizhifu
 * @since 2023/3/29
 */
@Service
public class EntityVariableService {
    private final GenConfig genConfig;
    private final GenFieldTypeService genFieldTypeService;
    private final GenTableDao genTableDao;
    public EntityVariableService(GenConfig genConfig, GenFieldTypeService genFieldTypeService, GenTableDao genTableDao) {
        this.genConfig = genConfig;
        this.genFieldTypeService = genFieldTypeService;
        this.genTableDao = genTableDao;
    }

    public Map<String, Object> getInjectVariablesMap(String tableName) {
        TableResp tableResp = genTableDao.selectTableByTableName(tableName);
        List<TableColumnResp> tableColumnResp = genTableDao.selectTableColumnByTableName(tableName);
        Map<String, Object> variablesMap = new HashMap<>();
        variablesMap.put("packageName", genConfig.getEntityPackageName() + ".domain.entity");
        variablesMap.put("importPackageList", getImportPackageList(tableColumnResp));
        return variablesMap;
    }

    private List<String> getImportPackageList(List<TableColumnResp> fields) {
        if (CollectionUtils.isEmpty(fields)) {
            return Lists.newArrayList();
        }
        LocalDate localDate = LocalDate.now();
        // 类型包名
        List<String> result = fields.stream().map(e -> genFieldTypeService.getByDataType(e.getDataType()).getPackageName()).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        // lombok
        result.add("import lombok.Data;");
//        //主键
//        boolean isExistPrimaryKey = fields.stream().filter(e -> e.getColumnKey() != null && e.getColumnKey()).findFirst().isPresent();
//        if (isExistPrimaryKey) {
//            result.add("import com.baomidou.mybatisplus.annotation.TableId;");
//        }
        Collections.sort(result);
        return result;
    }
}
