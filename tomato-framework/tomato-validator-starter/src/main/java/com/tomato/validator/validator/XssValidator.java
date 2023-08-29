package com.tomato.validator.validator;

import com.tomato.validator.annotation.Xss;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * xss 校验注解实现
 *
 * @author lizhifu
 */
public class XssValidator implements ConstraintValidator<Xss, String> {

	/**
	 * HTML
	 */
	private static final String HTML_PATTERN = "<(\\S*?)[^>]*>.*?|<.*? />";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null || value.length() == 0) {
			return true;
		}
		return !containsHtml(value);
	}

	public static boolean containsHtml(String value) {
		Pattern pattern = Pattern.compile(HTML_PATTERN);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

}