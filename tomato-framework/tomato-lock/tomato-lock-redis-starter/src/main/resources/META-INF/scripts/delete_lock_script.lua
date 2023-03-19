-- 判断数据 lockValue 是否相同
redis.log(redis.LOG_WARNING, 'try to delete lock: ' .. KEYS[1] .. ', owner: ' .. ARGV[1])
if redis.call('get', KEYS[1]) == ARGV[1]
then
    return redis.call('del', KEYS[1])
else
    return 0
end