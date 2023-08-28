local key = KEYS[1];
local count = tonumber(ARGV[1]);
local interval = tonumber(ARGV[2]);
local current = tonumber(redis.call('get', key) or "0")
if current + 1 > count then
  return 0
else
  redis.call("INCRBY", key, "1")
  redis.call("expire", key, interval)
  return current + 1
end
