if (redis.call('exists', KEYS[1]) == 1) then
    -- 删除原有的元素，暂时不考虑大数据量的情况,大数据的情况需要避免在 Redis 中阻塞太长时间
    redis.call('del', KEYS[1])
end

for i = 1, #ARGV, 2 do
    redis.call('')
    redis.call('ZADD', KEYS[1], ARGV[i], ARGV[i+1])
end