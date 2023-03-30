package com.tomato.gen.service.velocity;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板引擎工厂
 *
 * @author lizhifu
 * @since 2023/3/29
 */
@Component
public class VelocityServiceFactory implements InitializingBean {
    private static final Map<String, VelocityService> VELOCITY_SERVICE_MAP = new HashMap<>();
    private final ApplicationContext applicationContext;

    public VelocityServiceFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    public VelocityService getVelocityService(String template) {
        return VELOCITY_SERVICE_MAP.get(template);
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        applicationContext.getBeansOfType(VelocityService.class).forEach((k, v) -> VELOCITY_SERVICE_MAP.put(v.getTemplate(), v));
    }
}
