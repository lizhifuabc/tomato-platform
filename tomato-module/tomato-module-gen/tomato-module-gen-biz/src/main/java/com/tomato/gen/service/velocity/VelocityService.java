package com.tomato.gen.service.velocity;

import com.tomato.gen.domain.bo.TableBo;
import org.apache.velocity.VelocityContext;

/**
 * 模板引擎
 *
 * @author lizhifu
 * @since 2023/3/29
 */
public interface VelocityService {
    /**
     * 获取模板
     * @return 模板
     */
    String getTemplate();
    /**
     * 渲染模板
     * @param tableBo 表信息
     * @return 渲染结果
     */
    VelocityContext render(TableBo tableBo);
}
