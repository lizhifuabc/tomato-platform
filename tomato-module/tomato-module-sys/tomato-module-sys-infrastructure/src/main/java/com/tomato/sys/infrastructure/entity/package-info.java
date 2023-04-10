/**
 * Lombok
 * 可能会造成懒加载失效：Lombok 提供的 @Data 注解会自动生成 getter 和 setter 方法，而这些方法可能会在实体类被序列化时被调用，从而导致 JPA 的懒加载失效，如果这些实体类中包含了大量的关联关系，则可能会导致性能问题。
 * 可能会造成问题的难以排查：Lombok 自动生成的代码可能会隐藏一些问题，例如重复的字段、错误的类型等问题，这些问题可能会导致程序的异常行为，同时也会给排查问题带来困难。
 * 可能会造成 JPA 规范不一致：Lombok 自动生成的代码可能会与 JPA 规范不一致，例如 Lombok 自动生成的 toString() 方法可能会导致懒加载失效，从而违反 JPA 规范中对于懒加载的要求。
 *
 * @author lizhifu
 * @since 2023/4/9
 */
package com.tomato.sys.infrastructure.entity;