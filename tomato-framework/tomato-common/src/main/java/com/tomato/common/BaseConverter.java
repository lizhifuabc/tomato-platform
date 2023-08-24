package com.tomato.common;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * 通用转换器
 * @param <T> DTO
 *           DTO表示类型
 *           DTO：Data Transfer Object 数据传输对象
 *           DTO是一种设计模式，用于在软件系统之间传输数据。DTO是数据的容器，用于从一个层移动到另一个层。
 *           DTO不应该包含任何业务逻辑，只应该包含数据转移。
 * @param <U> Domain
 *           Domain表示类型
 *           Domain：领域对象
 *           领域对象是指那些反映业务状态和业务行为的对象，领域对象是业务逻辑的主要载体。
 *           领域对象是领域模型中最重要的元素，领域对象的状态和行为决定了业务逻辑的处理过程。
 *           领域对象是领域模型的基础，领域对象的设计直接影响到领域模型的质量。
 *
 * @author lizhifu
 * @since 2023/8/24
 */
@RequiredArgsConstructor
public class BaseConverter<T, U> {
    private final Function<T, U> fromDto;
    private final Function<U, T> fromEntity;

    /**
     * DTO转Entity
     * @param dto DTO
     * @return Entity
     */
    public final U convertFromDto(final T dto) {
        return fromDto.apply(dto);
    }
    /**
     * Entity转DTO
     * @param entity Entity
     * @return DTO
     */
    public final T convertFromEntity(final U entity) {
        return fromEntity.apply(entity);
    }
    /**
     * DTO集合转Entity集合
     * @param dtos DTO集合
     * @return Entity集合
     */
    public final List<U> createFromDtos(final Collection<T> dtos) {
        return dtos.stream().map(this::convertFromDto).toList();
    }
    /**
     * Entity集合转DTO集合
     * @param entities Entity集合
     * @return DTO集合
     */
    public final List<T> createFromEntities(final Collection<U> entities) {
        return entities.stream().map(this::convertFromEntity).toList();
    }
}
