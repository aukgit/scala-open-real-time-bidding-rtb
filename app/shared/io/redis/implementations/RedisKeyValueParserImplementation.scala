package shared.io.redis.implementations

import com.google.inject.Inject
import shared.io.redis.traits.{ RedisClientCoreProperties, RedisKeyValueParser }

class RedisKeyValueParserImplementation @Inject()(
  redisClientCore : RedisClientCoreProperties
)
  extends RedisCommonJsonParsingMechanismImplementation(redisClientCore) with RedisKeyValueParser {

}
