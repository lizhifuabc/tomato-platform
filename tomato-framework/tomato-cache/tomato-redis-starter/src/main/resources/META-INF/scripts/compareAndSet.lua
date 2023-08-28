local key = KEYS[1]
local oldValue = ARGV[1]
local newValue = ARGV[2]

local redisValue = redis.call('get', key)
if (redisValue == false or tonumber(redisValue) == tonumber(oldValue))
then
    redis.call('set', key, newValue)
    return true
else
    return false
end