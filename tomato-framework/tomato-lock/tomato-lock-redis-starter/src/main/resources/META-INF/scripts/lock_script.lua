-- 尝试获取锁
-- KEYS[1]：锁的名称
-- ARGV[1]：锁的持有者（通常是客户端标识符）
-- ARGV[2]：锁的过期时间
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