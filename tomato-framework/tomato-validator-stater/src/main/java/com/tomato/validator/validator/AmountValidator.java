package com.tomato.validator.validator;


import com.tomato.validator.annotation.Amount;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义金额注解
 *
 * @author lizhifu
 * @date 2021/9/14
 */
public class AmountValidator implements ConstraintValidator<Amount, BigDecimal> {
    /**
     * 表示金额的正则表达式
     */
    Pattern pattern = Pattern.compile("^[+]?([0-9]+(.[0-9]{1,2})?)$");
    @Override
    public boolean isValid(BigDecimal bigDecimal, ConstraintValidatorContext constraintValidatorContext) {
        Matcher match = pattern.matcher(bigDecimal.toString());
        return match.matches();
    }
    @Override
    public void initialize(Amount amount) {

    }
}
