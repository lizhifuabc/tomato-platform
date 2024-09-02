package com.tomato.common.excel.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;

/**
 * excel工具类
 *
 * @author lizhifu
 * @since 2024/9/2
 */
@Slf4j
public class ExcelUtil {
	/**
	 * 三种excel文件类型分别对应的响应头格式
	 */
	private static final String XLS_CONTENT_TYPE = "application/vnd.ms-excel";
	private static final String XLSX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	private static final String CSV_CONTENT_TYPE = "text/csv";


	/**
	 * 初始化模板填充导出场景的写对象
	 * @param fileName
	 * @param excelType
	 * @param templatePath
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public static ExcelWriter initExportFillWriter(String fileName, ExcelTypeEnum excelType, String templatePath, HttpServletResponse response) throws IOException {
		setResponse(fileName, excelType, response);
		return EasyExcelFactory.write(response.getOutputStream())
				.excelType(excelType)
				.withTemplate(templatePath).build();
	}


	/**
	 * 导出excel的通用方法
	 * @param clazz 传入excel导出的VO类
	 * @param excelData 传入需要导出的数据列表
	 * @param fileName 前导出的excel文件名称（不带文件后缀）
	 * @param excelType 导出的文件类型
	 * @param response 网络响应对象
	 * @throws IOException 异常
	 */
	public static void exportExcel(Class<?> clazz, List<?> excelData, String fileName, ExcelTypeEnum excelType, HttpServletResponse response) throws IOException {
		HorizontalCellStyleStrategy styleStrategy = setCellStyle();
		setResponse(fileName, excelType, response);
		ExcelWriterBuilder writeWork = EasyExcelFactory.write(response.getOutputStream(), clazz);
		writeWork.registerWriteHandler(styleStrategy).excelType(excelType).sheet().doWrite(excelData);
	}

	/**
	 * 校验excel文件的表头，与数据模型类的映射关系是否匹配
	 * @param headMap
	 * @param clazz
	 * @param fields
	 */
	public static void validateExcelTemplate(Map<Integer, String> headMap, Class<?> clazz, Field[] fields) {
		Collection<String> headNames = headMap.values();

		// 类上是否存在忽略excel字段的注解
		boolean classIgnore = clazz.isAnnotationPresent(ExcelIgnoreUnannotated.class);
		int count = 0;
		// 如果EasyExcel框架版本较低，请把这行代码放开，并将有效的字段列表返回，用于空值校验
		// List<Field> validFields = new ArrayList<>(fields.length);
		for (Field field : fields) {
			// 忽略序列化ID字段
			if ("serialVersionUID".equals(field.getName())) {
				continue;
			}
			// 如果字段上存在忽略注解，则跳过当前字段
			if (field.isAnnotationPresent(ExcelIgnore.class)) {
				continue;
			}
			ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
			if (null == excelProperty) {
				// 如果类上也存在忽略注解，则跳过所有未使用ExcelProperty注解的字段
				if (classIgnore) {
					continue;
				}
				// 如果检测到既未忽略、又未映射excel列的字段，则抛出异常提示模板不正确
				throw new ExcelAnalysisException("请检查导入的excel文件是否按模板填写!");
			}
			// 校验数据模型类上绑定的名称，是否与excel列名匹配
			String[] value = excelProperty.value();
			String name = value[0];
			if (name != null && 0 != name.length() && !headNames.contains(name)) {
				throw new ExcelAnalysisException("请检查导入的excel文件是否按模板填写!");
			}
			// 更新有效字段的数量
			count++;
			// validFields.add(field);
		}
		// 最后校验数据模型类的有效字段数量，与读到的excel列数量是否匹配
		if (headMap.size() != count) {
			throw new ExcelAnalysisException("请检查导入的excel文件是否按模板填写!");
		}
	}

	/**
	 * 判断整行单元格数据是否均为空（依赖于validateExcelTemplate()方法返回的有效字段列表）
	 * 说明：该方法适用于低版本的EasyExcel读取数据时校验，因为低版本的不会自动忽略空行
	 * @param data
	 * @param validFields
	 * @return
	 * @param <T>
	 */
	public static <T> boolean rowIsNull(T data, List<Field> validFields) {
		if (data instanceof String) {
			return "".equals(data);
		}
		try {
			List<Boolean> fieldNulls = new ArrayList<>(validFields.size());
			for (Field field : validFields) {
				field.setAccessible(true);
				Object value = field.get(data);
				if (Objects.isNull(value)) {
					fieldNulls.add(Boolean.TRUE);
				} else {
					fieldNulls.add(Boolean.FALSE);
				}
			}
			return fieldNulls.stream().allMatch(Boolean.TRUE::equals);
		} catch (Exception e) {
			log.error("读取数据行[{}]解析失败: {}", data, e.getMessage());
		}
		return true;
	}
	/**
	 * 上传Excel文件到OSS
	 * @param clazz
	 * @param excelData
	 * @param fileName
	 * @param excelType
	 * @return
	 * @throws IOException
	 */
	public static String exportExcelToOSS(Class<?> clazz, List<?> excelData, String fileName, ExcelTypeEnum excelType) throws IOException {
		HorizontalCellStyleStrategy styleStrategy = ExcelUtil.setCellStyle();
		fileName = fileName + excelType.getValue();
		File excelFile = File.createTempFile(fileName, excelType.getValue());
		EasyExcelFactory.write(excelFile, clazz).registerWriteHandler(styleStrategy).sheet().doWrite(excelData);
		String url = uploadFileToOss(excelFile);

		if (excelFile.exists()) {
			boolean flag = excelFile.delete();
			log.info("删除临时文件是否成功：{}", flag);
		}
		return url;
	}

	/**
	 * 上传OSS文件
	 * @param file
	 * @return
	 */
	public static String uploadFileToOss(File file) {
		// 省略上传至OSS的代
		return "resultUrl";
	}


	/**
	 * 设置单元格风格
	 * @return 单元格风格
	 */
	public static HorizontalCellStyleStrategy setCellStyle(){
		// 设置表头的样式（背景颜色、字体、居中显示）
		WriteCellStyle headStyle = new WriteCellStyle();
		// 设置表头的背景颜色
		headStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		WriteFont headFont = new WriteFont();
		headFont.setFontHeightInPoints((short)12);
		headFont.setBold(true);
		headStyle.setWriteFont(headFont);
		headStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);

		// 设置Excel内容策略(水平居中)
		WriteCellStyle cellStyle = new WriteCellStyle();
		cellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
		return new HorizontalCellStyleStrategy(headStyle, cellStyle);
	}

	/**
	 * 设置通用的响应头信息
	 * @param fileName
	 * @param excelType
	 * @param response
	 */
	public static void setResponse(String fileName, ExcelTypeEnum excelType, HttpServletResponse response) {
		// 对文件名进行UTF-8编码、拼接文件后缀名
		try {
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20") + excelType.getValue();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		switch (excelType) {
			case XLS:
				response.setContentType(XLS_CONTENT_TYPE);
				break;
			case XLSX:
				response.setContentType(XLSX_CONTENT_TYPE);
				break;
			case CSV:
				response.setContentType(CSV_CONTENT_TYPE);
				break;
		}
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + fileName);
	}

}
