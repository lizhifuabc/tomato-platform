package com.tomato.validator.validator;

import com.tomato.common.util.RegexPool;
import com.tomato.validator.annotation.Mobile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号校验
 *
 * @author lizhifu
 * @since 2021/12/10
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {

	private static final Pattern PHONE_PATTERN = Pattern.compile(RegexPool.MOBILE);

	@Override
	public void initialize(Mobile constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null || value.length() == 0) {
			return false;
		}
		Matcher m = PHONE_PATTERN.matcher(value);
		return m.matches();
	}

}
