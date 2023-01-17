local lockKey = redis.call('GET', KEYS[1])
if lockKey == ARGV[1] then
    -- 续期
    redis.call('PEXPIRE', KEYS[1], ARGV[2])
    return true
elseif not lockKey then
    -- 设置key
    redis.call('SET', KEYS[1], ARGV[1], 'PX', ARGV[2])
    return true
end
return false