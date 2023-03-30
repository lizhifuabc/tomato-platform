package com.tomato.gen.util;

import org.apache.velocity.VelocityContext;

/**
 * TODO
 *
 * @author lizhifu
 * @since 2023/3/29
 */
public class VelocityUtil {
    /**
     * 设置模板变量信息
     *
     * @return 模板列表
     */
    public static VelocityContext prepareContext() {
        VelocityContext velocityContext = new VelocityContext();
        return velocityContext;
    }
}
