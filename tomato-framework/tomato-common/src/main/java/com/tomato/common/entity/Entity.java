package com.tomato.common.entity;

import java.io.Serializable;

/**
 * Entity 实体：是指具有唯一标识的具体业务对象，比如用户、订单、商品等。
 * Entity 应该拥有自己的业务属性和行为，它们具有业务上的独立性，并且具有生命周期。
 * 在 DDD 中，Entity 应该被设计为不可变的对象，以保证数据的完整性和一致性。
 *
 * @author lizhifu
 * @since 2023/4/12
 */
public interface Entity extends Serializable {

}
