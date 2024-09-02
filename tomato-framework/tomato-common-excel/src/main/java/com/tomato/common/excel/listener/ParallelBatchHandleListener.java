package com.tomato.common.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.tomato.common.excel.util.ExcelUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.function.BiConsumer;

/**
 * 并行分批处理监听器（适用于大数据场景）
 */
@Slf4j
public class ParallelBatchHandleListener<T> extends AnalysisEventListener<T> {
	/**
	 * 每批的处理行数（可以根据实际情况做出调整）
	 */
	private static int BATCH_NUMBER = 1000;

	/**
	 * 临时存储读取到的excel数据
	 */
	private List<T> data;
	@Getter
	private int rows, batchNo;
	private boolean validateSwitch = true;

	/**
	 * 每批数据的业务逻辑处理器
	 */
	private final BiConsumer<List<T>, ParallelBatchHandleListener<T>> businessHandler;

	/**
	 * 并发阈值控制器
	 */
	@Getter
	private Semaphore concurrentThreshold;

	/**
	 * 用于校验excel模板正确性的字段
	 */
	private final Field[] fields;
	private final Class<T> clazz;

	public ParallelBatchHandleListener(Class<T> clazz, BiConsumer<List<T>, ParallelBatchHandleListener<T>> handle) {
		// 通过构造器为字段赋值，用于校验excel文件与模板是否匹配
		this(clazz, handle, BATCH_NUMBER);
	}

	public ParallelBatchHandleListener(Class<T> clazz, BiConsumer<List<T>, ParallelBatchHandleListener<T>> handle, int batchNumber) {
		// 通过构造器为字段赋值，用于校验excel文件与模板十分匹配
		this.clazz = clazz;
		this.fields = clazz.getDeclaredFields();
		// 初始化临时存储数据的集合，及外部传入的业务方法
		this.businessHandler = handle;
		BATCH_NUMBER = batchNumber;
		this.data = new ArrayList<>(BATCH_NUMBER);
	}

	/**
	 * 读取到excel头信息时触发，会将表头数据转为Map集合（用于校验导入的excel文件与模板是否匹配）
	 * 1：当前校验逻辑不适用于多行头模板（如果是多行头的文件，请关闭表头验证）；
	 * 2：使用当前监听器的导入场景，模型类不允许出现既未忽略、又未使用ExcelProperty注解的字段；
	 * @param headMap
	 * @param context
	 */
	@Override
	public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
		if (validateSwitch) {
			ExcelUtil.validateExcelTemplate(headMap, clazz, fields);
		}
	}

	/**
	 * 每成功解析一条excel数据行时触发
	 * @param row
	 * @param analysisContext
	 */
	@Override
	public void invoke(T row, AnalysisContext analysisContext) {
		data.add(row);
		// 判断当前已解析的数据是否达到本批上限，是则执行对应的业务处理
		if (data.size() >= BATCH_NUMBER) {
			// 更新读取到的总行数、批次数
			rows += data.size();
			batchNo++;

			// 如果开启了并发阈值控制，则先获取许可后再触发业务逻辑
			if (null != concurrentThreshold) {
				try {
					concurrentThreshold.acquire();
				} catch (InterruptedException e) {
					log.error("阻塞等待获取许可被中断，{}：{}", e, e.getMessage());
					return;
				}
			}

			// 触发业务逻辑处理
			this.businessHandler.accept(data, this);
			// 处理完本批数据后，使用新List替换旧List，旧List失去引用后会很快被GC
			data = new ArrayList<>(BATCH_NUMBER);
		}
	}

	/**
	 * 所有数据解析完之后触发
	 * @param analysisContext
	 */
	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {
		// 因为最后一批可能达不到指定的上限，所以解析完之后要做一次收尾处理
		if (!data.isEmpty()) {
			this.businessHandler.accept(data, this);
			// 更新读取到的总行数、批次数，以及清理集合辅助GC
			rows += data.size();
			batchNo++;
			data.clear();
		}
	}

	/**
	 * 关闭excel表头验证
	 */
	public void offValidate() {
		this.validateSwitch = false;
	}

	/**
	 * 初始化并发阈值（传入的数字则是允许并发处理的批次数）
	 * @param threshold
	 */
	public void initThreshold(int threshold) {
		if (null == this.concurrentThreshold) {
			this.concurrentThreshold = new Semaphore(threshold);
		}
	}
}