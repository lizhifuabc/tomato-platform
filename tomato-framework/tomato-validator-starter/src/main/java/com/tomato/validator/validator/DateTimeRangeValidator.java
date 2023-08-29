package com.tomato.validator.validator;

import com.tomato.validator.annotation.DateTimeRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 日期时间范围校验
 *
 * @author lizhifu
 * @since 2022/12/19
 */
public class DateTimeRangeValidator implements ConstraintValidator<DateTimeRange, Object> {

	private DateTimeRange dateTimeRange;

	@Override
	public void initialize(DateTimeRange dateTimeRange) {
		this.dateTimeRange = dateTimeRange;
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null) {
			return false;
		}
		// 获取输入时间
		LocalDateTime input = getByValue(value);

		ChronoUnit unit = dateTimeRange.unit();
		int min = dateTimeRange.min();
		int max = dateTimeRange.max();

		LocalDateTime now = LocalDateTime.now();
		return input.isAfter(now.plus(min, unit)) && input.isBefore(now.plus(max, unit));
	}

	/**
	 * 时间格式转换
	 * @param value
	 * @return
	 */
	private LocalDateTime getByValue(Object value) {
		if (value instanceof LocalDateTime) {
			return (LocalDateTime) value;
		}
		return LocalDateTime.parse((String) value, DateTimeFormatter.ofPattern(dateTimeRange.pattern()));
	}

}
