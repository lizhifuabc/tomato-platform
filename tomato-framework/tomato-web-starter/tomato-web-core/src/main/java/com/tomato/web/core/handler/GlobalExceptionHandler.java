package com.tomato.web.core.handler;

import com.tomato.common.exception.BusinessException;
import com.tomato.common.resp.Resp;
import com.tomato.common.constants.CommonRespCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import java.util.List;

import static com.tomato.common.constants.CommonRespCode.INTERNAL_SERVER_ERROR;

/**
 * 全局异常处理器|
 * <p>
 * 全局拦截异常注解:@RestControllerAdvice = @ControllerAdvice + @ResponseBody
 * </p>
 *
 * @author lizhifu
 * @since 2022/12/7
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 业务异常处理
	 */
	@ResponseBody
	@ExceptionHandler(BusinessException.class)
	public Resp<Void> businessExceptionHandler(HttpServletRequest request, BusinessException ex) {
		if (ex.getCause() != null) {
			log.error("全局异常处理器,BusinessException,请求地址{},请求方法{},业务异常处理{}", getCurrentRequestUrl(),request.getMethod() ,ex.getCause());
			return Resp.buildFailure(CommonRespCode.INTERNAL_SERVER_ERROR.code(), ex.getMessage());
		}
		log.error("全局异常处理器,BusinessException,请求地址{},请求方法{},业务异常处理{}", getCurrentRequestUrl(),request.getMethod() ,ex.toString());
		return Resp.buildFailure(CommonRespCode.INTERNAL_SERVER_ERROR.code(), ex.getMessage());
	}

	/**
	 * 参数校验异常处理
	 */
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Resp<Void> validExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		List<String> error = fieldErrors.stream()
			.map(field -> field.getField() + ":" + field.getRejectedValue() + ":" + field.getDefaultMessage())
			.toList();
		log.error("全局异常处理器,MethodArgumentNotValidException,请求地址{},请求方法{},参数校验异常{}", getCurrentRequestUrl(),request.getMethod() ,error);
		return Resp.buildFailure(CommonRespCode.FAIL.code(), String.valueOf(error));
	}

	/**
	 * 其他所有 Exception
	 */
	@ExceptionHandler(value = Exception.class)
	public Resp<Void> otherExceptionHandler(HttpServletRequest request, Throwable throwable) {
		log.error("全局异常处理器,Exception,请求地址{},请求方法{},未知异常", getCurrentRequestUrl(),request.getMethod() ,throwable);
		return Resp.buildFailure(INTERNAL_SERVER_ERROR.code(), INTERNAL_SERVER_ERROR.msg());
	}

	/**
	 * 获取当前请求url
	 */
	private String getCurrentRequestUrl() {
		RequestAttributes request = RequestContextHolder.getRequestAttributes();
		if (null == request) {
			return null;
		}
		ServletRequestAttributes servletRequest = (ServletRequestAttributes) request;
		return servletRequest.getRequest().getRequestURI();
	}
	/**
	 * 主键或UNIQUE索引，数据重复异常
	 */
	@ExceptionHandler(DuplicateKeyException.class)
	public Resp<Void> handleDuplicateKeyException(DuplicateKeyException e, HttpServletRequest request) {
		log.error("全局异常处理器,DuplicateKeyException,请求地址{},请求方法{},数据库中已存在记录{}", getCurrentRequestUrl(),request.getMethod() ,e.getMessage());
		return Resp.buildFailure("数据库中已存在该记录，请联系管理员确认");
	}
	/**
	 * 请求方式不支持
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Resp<Void> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
													   HttpServletRequest request) {
		log.error("全局异常处理器,HttpRequestMethodNotSupportedException,请求地址:{},请求方法:{},不支持请求:{}", getCurrentRequestUrl(),request.getMethod() ,e.getMessage());
		return Resp.buildFailure(e.getMessage());
	}
	/**
	 * 请求路径中缺少必需的路径变量
	 */
	@ExceptionHandler(MissingPathVariableException.class)
	public Resp<Void> handleMissingPathVariableException(MissingPathVariableException e, HttpServletRequest request) {
		log.error("全局异常处理器,MissingPathVariableException,请求地址:{},请求方法:{},请求路径中缺少必需的路径变量:{}", getCurrentRequestUrl(),request.getMethod() ,e.getVariableName());
		return Resp.buildFailure(String.format("请求路径中缺少必需的路径变量[%s]", e.getVariableName()));
	}
	/**
	 * 请求参数类型不匹配
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public Resp<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
		log.error("全局异常处理器,MethodArgumentTypeMismatchException,请求地址:{},请求方法:{},请求参数类型不匹配,参数[{}]要求类型为：'{}'，但输入值为：'{}'", getCurrentRequestUrl(),request.getMethod() ,e.getName(), e.getRequiredType(), e.getValue());
		return Resp.buildFailure(String.format("请求参数类型不匹配，参数[%s]要求类型为：'%s'，但输入值为：'%s'", e.getName(), e.getRequiredType(), e.getValue()));
	}

}
