package shared.io.redis.implementations

import com.google.inject.Inject
import shared.io.redis.traits.{ RedisClientCoreProperties, RedisKeyValueParser }

class RedisClientAgentImplementation @Inject()(redisClientCore : RedisClientCoreProperties)
  extends RedisKeyValueParserImplementation(redisClientCore)
    with RedisClientCoreProperties
    with RedisKeyValueParser
