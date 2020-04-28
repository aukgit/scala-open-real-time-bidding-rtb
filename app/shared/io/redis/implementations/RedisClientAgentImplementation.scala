package shared.io.redis.implementations

import com.google.inject.Inject
import shared.io.redis.traits.{ RedisClientCorePropertiesContracts, RedisKeyValueParser }

class RedisClientAgentImplementation @Inject()(redisClientCore : RedisClientCorePropertiesContractsImplementation)
  extends RedisKeyValueParserImplementation(redisClientCore)
    with RedisClientCorePropertiesContracts
    with RedisKeyValueParser
