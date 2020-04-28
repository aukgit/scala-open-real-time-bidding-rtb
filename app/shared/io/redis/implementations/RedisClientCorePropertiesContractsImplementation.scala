package shared.io.redis.implementations

import com.redis.RedisClient
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.{ ConfigModel, DomainPortModel }
import shared.io.redis.traits.RedisClientCorePropertiesContracts

class RedisClientCorePropertiesContractsImplementation(appManager : AppManager) extends RedisClientCorePropertiesContracts {
  lazy val config : ConfigModel = appManager.config
  lazy val redisServerConfigurationInfo : DomainPortModel = config.server.redisServer
  lazy val redisClient : RedisClient = new RedisClient(redisServerConfigurationInfo.domain, 6379)
}
