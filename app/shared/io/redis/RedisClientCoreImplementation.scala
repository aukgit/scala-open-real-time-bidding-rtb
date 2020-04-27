package shared.io.redis

import com.redis.RedisClient
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.{ ConfigModel, DomainPortModel }
import shared.io.redis.traits.RedisClientCore

class RedisClientCoreImplementation(appManager: AppManager) extends RedisClientCore{
  lazy val config : ConfigModel = appManager.config
  lazy val redisServerConfigurationInfo : DomainPortModel = config.server.redisServer
  lazy val redisClient : RedisClient = new RedisClient(redisServerConfigurationInfo.domain, 6379)
}
