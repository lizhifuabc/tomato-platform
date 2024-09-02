package com.tomato.common.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.tomato.common.excel.util.ExcelUtil;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通用的excel导入监听器
 * 不管是什么业务场景下的Excel导入，
 * 都会经过“解析文件、提取数据、进行业务处理”这三步，除开第三步外，前两步逻辑是相同的
 *
 * @author lizhifu
 * @since 2024/9/2
 */
public class CommonListener <T> extends AnalysisEventListener<T> {
	/**
	 * 创建list集合封装最终的数据
	 * -- GETTER --
	 *  解析到的所有数据
	 *
	 * @return

	 */
	@Getter
	private final List<T> data;

	/**
	 * 字段列表
 	 */
	private final Field[] fields;

	/**
	 * excel中的字段
	 */
	private final Class<T> clazz;

	/**
	 * 是否excel表头验证，默认是true
	 */
	private boolean validateSwitch = true;

	public CommonListener(Class<T> clazz) {
		fields = clazz.getDeclaredFields();
		this.clazz = clazz;
		this.data = new ArrayList<T>();
	}

	/**
	 * 解析每一条数据
	 * @param row
	 * @param analysisContext
	 */
	@Override
	public void invoke(T row, AnalysisContext analysisContext) {
		data.add(row);
	}

	/**
	 * 读取到excel头信息时触发，会将表头数据转为Map集合
	 * @param headMap
	 * @param context
	 */
	@Override
	public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
		// 校验读到的excel表头是否与数据模型类匹配
		if (validateSwitch) {
			ExcelUtil.validateExcelTemplate(headMap, clazz, fields);
		}
	}

	/**
	 * 所有数据解析完之后触发
	 * @param analysisContext
	 */
	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {}

	/**
	 * 关闭excel表头验证
	 */
	public void offValidate() {
		this.validateSwitch = false;
	}
}
