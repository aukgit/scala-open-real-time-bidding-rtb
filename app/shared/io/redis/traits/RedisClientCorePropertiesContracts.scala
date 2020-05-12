package shared.io.redis.traits

import com.redis.RedisClient
import shared.com.ortb.model.config.DomainPortModel
import shared.io.helpers.TryHelper

import scala.util.Try

trait RedisClientCorePropertiesContracts {
  val redisClient : RedisClient
  val redisServerConfigurationInfo : DomainPortModel

  def isServerActive : Boolean = TryHelper.TryResult(Try(redisClient != null)).isSuccess && redisClient != null
}
