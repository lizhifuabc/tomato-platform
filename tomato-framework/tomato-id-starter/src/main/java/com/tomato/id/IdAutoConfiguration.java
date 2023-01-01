package com.tomato.id;

import com.tomato.id.generator.IdGenerator;
import com.tomato.id.generator.impl.DefaultIdGenerator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * ID 生成
 *
 * @author lizhifu
 * @since 2022/12/15
 */
@AutoConfiguration
public class IdAutoConfiguration {
}
