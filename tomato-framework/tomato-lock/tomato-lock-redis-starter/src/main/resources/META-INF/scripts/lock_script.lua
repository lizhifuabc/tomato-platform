-- Description: 分布式锁脚本
-- 涉及到的命令：setnx、expire、get
-- setnx key value：设置 key 的值为 value，当且仅当 key 不存在
-- expire key seconds：为 key 设置过期时间，单位为秒
-- get key：获取 key 的值

-- 尝试获取锁
-- KEYS[1]：锁的名称
-- ARGV[1]：锁的持有者（通常是客户端标识符）(uuid)
-- ARGV[2]：锁的过期时间
redis.log(redis.LOG_WARNING, 'try to get lock: ' .. KEYS[1] .. ', owner: ' .. ARGV[1] .. ', expire: ' .. ARGV[2])
if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then
    -- 获取到锁，设置过期时间并返回成功
    redis.call('expire', KEYS[1], ARGV[2])
    return true
elseif redis.call('get', KEYS[1]) == ARGV[1] then
    -- 已经获取到锁，再次获取锁的持有者是自己，更新过期时间并返回成功
    redis.call('expire', KEYS[1], ARGV[2])
    return true
else
    -- 未获取到锁，返回失败
    return false
end