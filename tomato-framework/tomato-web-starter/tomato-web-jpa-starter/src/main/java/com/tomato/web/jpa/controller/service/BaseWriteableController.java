package com.tomato.web.jpa.controller.service;

import com.tomato.common.entity.AbstractEntity;
import com.tomato.common.resp.Resp;
import com.tomato.jpa.domain.service.BaseWriteableService;

import java.io.Serializable;

/**
 * BaseWriteableController
 *
 * @author lizhifu
 * @since 2023/4/26
 */
public interface BaseWriteableController<E extends AbstractEntity, ID extends Serializable>
        extends BaseController {
    /**
     * 获取Service
     *
     * @return Service
     */
    BaseWriteableService<E, ID> getWriteableService();
    /**
     * 保存或更新实体
     *
     * @param domain 实体参数
     * @return 用Result包装的实体
     */

    default Resp<E> saveOrUpdate(E domain) {
        E savedDomain = getWriteableService().saveOrUpdate(domain);
        return result(savedDomain);
    }

    /**
     * 删除数据
     *
     * @param id 实体ID
     * @return 用Result包装的信息
     */
    default Resp<Void> delete(ID id) {
        Resp<Void> result = result(String.valueOf(id));
        getWriteableService().deleteById(id);
        return result;
    }
}
