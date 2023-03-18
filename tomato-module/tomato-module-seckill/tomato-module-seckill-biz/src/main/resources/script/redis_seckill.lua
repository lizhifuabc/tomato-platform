-- 尝试从 Redis 中减少库存，并返回减少后的库存
-- 如果库存不足，返回 0
-- KEYS[1] 表示库存 key
-- KEYS[2] 表示已售数量 key
-- ARGV[1] 表示减少的库存量
-- ARGV[2] 表示当前时间戳
if tonumber(redis.call("get", KEYS[1])) < tonumber(ARGV[1]) then
    return 0
else
    redis.call("incrby", KEYS[2], ARGV[1])
    redis.call("decrby", KEYS[1], ARGV[1])
    redis.call("zadd", KEYS[3], ARGV[2], ARGV[2])
    return redis.call("get", KEYS[1])
end
