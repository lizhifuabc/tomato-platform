local key = KEYS[1]
-- 先判断商品id是否存在，如果不存在则直接返回
if (redis.call('exists', key) == 1) then
    local stock = tonumber(redis.call('get', key));
    -- 获取该商品id的库存，判断库存如果是-1，则直接返回，表示不限制库存
    if (stock == -1) then
        -- 无库存限制
        return 1;
    end
    -- 如果库存大于0，则扣减库存。
    if (stock > 0) then
        redis.call('incrby', key, -1);
        -- 返回剩余库存数
        return stock - 1;
    end
    -- 如果库存等于0，是直接返回，表示库存不足。
    return 0;
end
-- 缓存不存在
return -1;