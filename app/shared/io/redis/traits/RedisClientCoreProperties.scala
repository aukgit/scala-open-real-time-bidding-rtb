package shared.io.redis.traits

import com.redis.RedisClient
import shared.com.ortb.model.config.DomainPortModel
import shared.io.jsonParse.traits.CommonJsonParsingMechanism

trait RedisClientCoreProperties {
  val redisClient : RedisClient
  val redisServerConfigurationInfo : DomainPortModel
}
