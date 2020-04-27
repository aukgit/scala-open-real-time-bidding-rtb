package shared.io.redis.traits

import com.redis.RedisClient
import shared.com.ortb.model.config.DomainPortModel

trait RedisClientCore {
  val redisClient : RedisClient
  val redisServerConfigurationInfo : DomainPortModel
}
