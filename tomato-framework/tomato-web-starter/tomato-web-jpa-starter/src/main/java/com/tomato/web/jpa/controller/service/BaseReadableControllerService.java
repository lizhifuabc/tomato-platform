package com.tomato.web.jpa.controller.service;

import com.tomato.common.entity.AbstractEntity;
import com.tomato.common.resp.Resp;
import com.tomato.jpa.domain.service.BaseReadableService;
import com.tomato.web.core.controller.Controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * BaseReadableController
 *
 * @author lizhifu
 * @since 2023/4/26
 */
public interface BaseReadableControllerService<E extends AbstractEntity, ID extends Serializable> extends Controller, BaseControllerService {
    /**
     * 获取Service
     *
     * @return Service
     */
    BaseReadableService<E, ID> getReadableService();
    /**
     * 查询分页数据
     *
     * @param pageNumber 当前页码，起始页码 0
     * @param pageSize   每页显示数据条数
     * @return {@link Resp}
     */
    default Resp<Map<String, Object>> findByPage(Integer pageNumber, Integer pageSize) {
        Page<E> pages = getReadableService().findByPage(pageNumber, pageSize);
        return result(pages);
    }

    /**
     * 查询分页数据
     *
     * @param pageNumber 当前页码, 起始页码 0
     * @param pageSize   每页显示的数据条数
     * @param direction  {@link org.springframework.data.domain.Sort.Direction}
     * @param properties 排序的属性名称
     * @return 分页数据
     */
    default Resp<Map<String, Object>> findByPage(Integer pageNumber, Integer pageSize, Sort.Direction direction, String... properties) {
        Page<E> pages = getReadableService().findByPage(pageNumber, pageSize, direction, properties);
        return result(pages);
    }

    default Resp<List<E>> findAll() {
        List<E> domains = getReadableService().findAll();
        return result(domains);
    }

    default Resp<E> findById(ID id) {
        E domain = getReadableService().findById(id).orElse(null);
        return result(domain);
    }
}
