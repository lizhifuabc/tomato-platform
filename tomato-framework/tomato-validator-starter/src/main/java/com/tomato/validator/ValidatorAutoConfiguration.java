package com.tomato.validator;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * validator auto configuration
 *
 * @author lizhifu
 * @date 2022/5/16
 */
@AutoConfiguration
public class ValidatorAutoConfiguration {
    /**
     * Spring Validation 默认会校验完所有字段，然后才抛出异常。<br>
     * failFast 模式：一旦校验失败就立即返回。
     * @return
     */
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 快速失败模式
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
