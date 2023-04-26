package com.tomato.web.jpa.controller;

import com.tomato.common.entity.AbstractEntity;
import com.tomato.web.jpa.controller.service.BaseReadableController;
import com.tomato.web.jpa.controller.service.BaseWriteableController;

import java.io.Serializable;

/**
 * BaseController
 *
 * @author lizhifu
 * @since 2023/4/26
 */
public abstract class AbstractBaseController<E extends AbstractEntity, ID extends Serializable>
        implements BaseReadableController<E,ID>, BaseWriteableController<E, ID> {
}
