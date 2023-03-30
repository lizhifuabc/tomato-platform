package com.tomato.gen.service;

import com.google.common.base.CaseFormat;
import com.tomato.gen.config.GenConfig;
import com.tomato.gen.constant.TemplateConstant;
import com.tomato.gen.dao.GenTableDao;
import com.tomato.gen.domain.bo.TableBo;
import com.tomato.gen.domain.resp.TableColumnResp;
import com.tomato.gen.domain.resp.TableResp;
import com.tomato.gen.service.velocity.VelocityService;
import com.tomato.gen.service.velocity.VelocityServiceFactory;
import com.tomato.gen.util.VelocityInitializer;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

/**
 * 代码生成
 *
 * @author lizhifu
 * @since 2023/3/29
 */
@Service
public class GenTableService {
    private final GenTableDao genTableDao;
    private final GenConfig genConfig;
    private final VelocityServiceFactory velocityServiceFactory;

    private final GenFieldTypeService genFieldTypeService;
    public GenTableService(GenTableDao genTableDao, GenConfig genConfig, VelocityServiceFactory velocityServiceFactory, GenFieldTypeService genFieldTypeService) {
        this.genTableDao = genTableDao;
        this.genConfig = genConfig;
        this.velocityServiceFactory = velocityServiceFactory;
        this.genFieldTypeService = genFieldTypeService;
    }

    public void genCode(String tableName) {
        // 1.查询表信息
        TableResp table = genTableDao.selectTableByTableName(tableName);
        // 存在表前缀，去除表前缀，生成类名；不存在，直接生成类名
        String className = Optional.ofNullable(genConfig.getPrefix()).map(prefix -> table.getTableName().replace(prefix, "")).orElse(table.getTableName());
        table.setUpperTableName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, className));
        // 2.查询列信息
        List<TableColumnResp> tableColumnList = genTableDao.selectTableColumnByTableName(tableName);
        tableColumnList.forEach(tableColumn -> {
            // 列名转换成Java属性名
            String attrName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableColumn.getColumnName());
            tableColumn.setLowerColumnName(attrName);
        });
        // 3.生成代码
        VelocityInitializer.initVelocity();
        // 渲染模板
        TableBo tableBo = new TableBo();
        tableBo.setTable(table);
        tableBo.setTableColumnList(tableColumnList);
        VelocityService velocityService = velocityServiceFactory.getVelocityService(TemplateConstant.VM_DAO);
        VelocityContext context = velocityService.render(tableBo);

        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate(velocityService.getTemplate(), VelocityInitializer.UTF_8);
        tpl.merge(context, sw);

        System.out.println(sw.toString());
    }
}
