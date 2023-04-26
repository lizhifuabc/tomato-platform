package com.tomato.web.core.controller;

import com.tomato.common.entity.Entity;
import com.tomato.common.resp.Resp;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author lizhifu
 * @since 2023/4/26
 */
public interface Controller {
    /**
     * 数据实体转换为统一响应实体
     *
     * @param domain 数据实体
     * @param <E>    {@link Entity} 子类型
     * @return {@link Resp} Entity
     */
    default <E extends Entity> Resp<E> result(E domain) {
        return Resp.of(domain);
    }
    /**
     * 数据操作结果转换为统一响应实体
     *
     * @param parameter 数据ID
     * @param <ID>      ID 数据类型
     * @return {@link Resp} String
     */
    default <ID extends Serializable> Resp<Void> result(ID parameter) {
        if (ObjectUtils.isNotEmpty(parameter)) {
            return Resp.buildSuccess("操作成功！");
        } else {
            return Resp.buildFailure("操作失败！");
        }
    }
    /**
     * 数据列表转换为统一响应实体
     *
     * @param domains 数据实体 List
     * @param <E>     {@link Entity} 子类型
     * @return {@link Resp} List
     */
    default <E extends Entity> Resp<List<E>> result(List<E> domains) {
        return Resp.of(domains);
    }

    /**
     * 数据 Map 转换为统一响应实体
     *
     * @param map 数据 Map
     * @return {@link Resp} Map
     */
    default Resp<Map<String, Object>> result(Map<String, Object> map) {
        return Resp.of(map);
    }
}
