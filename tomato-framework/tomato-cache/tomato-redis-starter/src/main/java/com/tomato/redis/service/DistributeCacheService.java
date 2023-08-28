package com.tomato.redis.service;


import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 分布式缓存
 * <p>缓存击穿
 * <p>缓存穿透
 * <p>缓存雪崩
 *
 * @author lizhifu
 * @since 2023/7/13
 */
public interface DistributeCacheService {
    /**
     * 添加缓存
     * @param key 缓存key
     * @param value 缓存value
     */
    void set(String key, Object value);

    /**
     * 获取缓存
     * @param key 缓存key
     * @return 缓存value
     */
    String get(String key);

    /**
     * 添加缓存，设置过期时间
     * @param key 缓存key
     * @param value 缓存value
     * @param timeout 过期时间
     * @param unit 单位
     */
    void setExpire(String key, Object value, Long timeout, TimeUnit unit);

    /**
     * 添加缓存，设置逻辑过期时间，实际缓存并不会过期，只是逻辑上过期，需要业务方判断
     * @param key 缓存key
     * @param value 缓存value
     * @param timeout 逻辑过期时间
     * @param unit 单位
     */
    void setLogicalExpire(String key, Object value, Long timeout, TimeUnit unit);

    // 以下是获取缓存的方法，防止缓存穿透 start

    /**
     * 获取缓存，防止缓存穿透
     * <p>1.查询缓存数据
     * <p>2.缓存存在数据，直接返回
     * <p>3.缓存中存储的是 "" (空字符串)，直接返回 null
     * <p>4.从数据库查询数据,查询数据数据为空，缓存 "" (空字符串)并设置过期时间，返回 null
     * <p>5.从数据库查询数据,查询数据数据不为空，缓存数据并设置过期时间，返回数据
     *
     * @param keyPrefix 缓存key前缀
     * @param id 缓存的业务标识
     * @param type 缓存对象类型
     * @param dbFunction 数据库查询 Function 函数
     * @param timeout 过期时间
     * @param unit 单位
     * @return 业务数据
     * @param <R> 结果泛型
     * @param <ID> 查询数据库参数泛型，也是参数泛型类型
     */
    <R,ID> R get(String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFunction, Long timeout, TimeUnit unit);

    /**
     * 获取缓存，防止缓存穿透
     * @param keyPrefix 缓存key前缀
     * @param type 缓存对象类型
     * @param dbFunction 无参数查询数据库数据
     * @param timeout 过期时间
     * @param unit 单位
     * @return 返回业务数据
     * @param <R> 结果泛型
     */
    <R> R get(String keyPrefix, Class<R> type, Supplier<R> dbFunction, Long timeout, TimeUnit unit);
    /**
     * 获取缓存，防止缓存穿透
     * @param keyPrefix 缓存key前缀
     * @param id 缓存的业务标识，
     * @param type 缓存对象类型
     * @param dbFunction 查询数据库的Function函数
     * @param timeout 过期时间
     * @param unit 单位
     * @return 返回业务数据
     * @param <R> 结果泛型
     * @param <ID> 查询数据库参数泛型，也是参数泛型类型
     */
    <R,ID> List<R> getList(String keyPrefix, ID id, Class<R> type, Function<ID, List<R>> dbFunction, Long timeout, TimeUnit unit);

    /**
     * 获取缓存，防止缓存穿透
     * @param keyPrefix 缓存key前缀
     * @param type 缓存对象类型
     * @param dbFunction 无参数查询数据库数据
     * @param timeout 过期时间
     * @param unit 单位
     * @return 返回业务数据
     * @param <R> 结果泛型
     */
    <R> List<R> getList(String keyPrefix, Class<R> type, Supplier<List<R>> dbFunction, Long timeout, TimeUnit unit);

    // 以上是获取缓存的方法，防止缓存穿透 end


    // 以下是获取缓存的方法，通过分布式锁防止缓存击穿 start
    // 1.查询缓存数据
    // 2.缓存数据不存在，加锁，查询数据库数据，缓存数据，释放锁
    // 2.缓存数据不存在，新开线程重建缓存：加锁，查询数据库数据，缓存数据，释放锁
    // 3.缓存数据存在，直接返回

    /**
     * 获取缓存
     * @param keyPrefix 缓存key前缀
     * @param id 缓存业务标识，也是查询数据库的参数
     * @param type 缓存对象类型
     * @param dbFunction 查询数据库的Function函数
     * @param timeout 缓存逻辑过期时长
     * @param unit 缓存逻辑过期单位
     * @return 业务数据
     * @param <R> 结果数据泛型类型
     * @param <ID> 查询数据库泛型类型，也是参数泛型类型
     */
    <R, ID> R getLogicalExpire(String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFunction, Long timeout, TimeUnit unit);

    /**
     * 获取缓存
     * @param keyPrefix 缓存key前缀
     * @param type 缓存对象类型
     * @param dbFunction 无参数查询数据库数据
     * @param timeout 过期时间
     * @param unit 单位
     * @return 返回业务数据
     * @param <R> 结果泛型
     */
    <R> R getLogicalExpire(String keyPrefix, Class<R> type, Supplier<R> dbFunction, Long timeout, TimeUnit unit);
    /**
     * 获取缓存
     * @param keyPrefix 缓存key前缀
     * @param id 缓存业务标识，也是查询数据库的参数
     * @param type 缓存对象类型
     * @param dbFunction 查询数据库的Function函数
     * @param timeout 缓存逻辑过期时长
     * @param unit 缓存逻辑过期单位
     * @return 业务数据
     * @param <R> 结果数据泛型类型
     * @param <ID> 查询数据库泛型类型，也是参数泛型类型
     */
    <R, ID> List<R> getLogicalExpireList(String keyPrefix, ID id, Class<R> type, Function<ID, List<R>> dbFunction, Long timeout, TimeUnit unit);

    /**
     * 获取缓存
     * @param keyPrefix 缓存key前缀
     * @param type 缓存对象类型
     * @param dbFunction 无参数查询数据库数据
     * @param timeout 过期时间
     * @param unit 单位
     * @return 返回业务数据
     * @param <R> 结果泛型
     */
    <R> List<R> getLogicalExpireList(String keyPrefix, Class<R> type, Supplier<List<R>> dbFunction, Long timeout, TimeUnit unit);

    // 以上是获取缓存的方法，通过分布式锁防止缓存击穿 end

    /**
     * 互斥锁方式获取缓存数据，其他线程访问不到数据重试
     * @param keyPrefix 缓存key前缀
     * @param id 缓存业务标识，也是查询数据库的参数
     * @param type 缓存对象类型
     * @param dbFunction 查询数据库的Function函数
     * @param timeout 缓存时长
     * @param unit 单位
     * @return 业务数据
     * @param <R> 结果数据泛型类型
     * @param <ID> 查询数据库泛型类型，也是参数泛型类型
     */
    <R, ID> R getWithLock(String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFunction, Long timeout, TimeUnit unit);

    /**
     * 互斥锁方式获取缓存数据，其他线程访问不到数据重试
     * @param keyPrefix 缓存key前缀
     * @param type 缓存对象类型
     * @param dbFunction 无参数查询数据库数据
     * @param timeout 缓存时长
     * @param unit 单位
     * @return 返回业务数据
     * @param <R> 结果泛型
     */
    <R> R getWithLock(String keyPrefix, Class<R> type, Supplier<R> dbFunction, Long timeout, TimeUnit unit);
    /**
     * 互斥锁方式获取缓存数据，其他线程访问不到数据重试
     * @param keyPrefix 缓存key前缀
     * @param id 缓存业务标识，也是查询数据库的参数
     * @param type 缓存对象类型
     * @param dbFunction 查询数据库的Function函数
     * @param timeout 缓存时长
     * @param unit 单位
     * @return 业务数据
     * @param <R> 结果数据泛型类型
     * @param <ID> 查询数据库泛型类型，也是参数泛型类型
     */
    <R, ID> List<R> getWithLockList(String keyPrefix, ID id, Class<R> type, Function<ID, List<R>> dbFunction, Long timeout, TimeUnit unit);

    /**
     * 互斥锁方式获取缓存数据，其他线程访问不到数据重试
     * @param keyPrefix 缓存key前缀
     * @param type 缓存对象类型
     * @param dbFunction 无参数查询数据库数据
     * @param timeout 缓存时长
     * @param unit 单位
     * @return 返回业务数据
     * @param <R> 结果泛型
     */
    <R> List<R> getWithLockList(String keyPrefix, Class<R> type, Supplier<List<R>> dbFunction, Long timeout, TimeUnit unit);
}
