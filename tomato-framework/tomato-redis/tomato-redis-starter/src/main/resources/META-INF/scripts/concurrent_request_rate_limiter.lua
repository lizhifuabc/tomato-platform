local key = KEYS[1]

local capacity = tonumber(ARGV[2])
local timestamp = tonumber(ARGV[3])
local id = KEYS[2]

local count = redis.call("zcard", key)
local allowed = 0

if count < capacity then
  redis.call("zadd", key, timestamp, id)
  allowed = 1
  count = count + 1
end
-- redis.call("setex", key, timestamp)
return { allowed, count }
