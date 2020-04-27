package shared.io.redis.implementations

import com.google.inject.Inject
import com.redis.RedisClient
import com.redis.api.StringApi
import shared.com.ortb.model.config.DomainPortModel
import shared.io.redis.traits.{ RedisClientCoreProperties, RedisKeyValueParser }

import scala.concurrent.duration.Duration

class RedisKeyValueParserImplementation @Inject()(
  val redisClientCore : RedisClientCoreProperties
)
  extends RedisCommonJsonParsingMechanismImplementation(redisClientCore)
    with RedisKeyValueParser
    with RedisClientCoreProperties {

  override lazy val redisClient : RedisClient = redisClientCore.redisClient
  override lazy val redisServerConfigurationInfo : DomainPortModel= redisClientCore.redisServerConfigurationInfo

  override def set(key : String, value : String, whenSet : StringApi.SetBehaviour, expire : Duration) : Unit =
    redisClient.set(key, value, whenSet, expire)

  override def setInt(key : String, value : Option[Int], whenSet : StringApi.SetBehaviour, expire : Duration) : Unit =
    redisClient.set(key, value.get, whenSet, expire)

  override def setLong(key : String, value : Option[Long], whenSet : StringApi.SetBehaviour, expire : Duration) : Unit = ???

  override def setDouble(key : String, value : Option[Double], whenSet : StringApi.SetBehaviour, expire : Duration) : Unit = ???

  override def appendItemToList(listKey : String, value : String, whenSet : StringApi.SetBehaviour, expire : Duration) : Unit = ???

  override def setList[T](key : String, value : Iterable[T], whenSet : StringApi.SetBehaviour, expire : Duration) : Unit = ???

  override def getListAs[T](key : String) : Iterable[T] = ???

  override def setStringList(key : String, value : Iterable[String], whenSet : StringApi.SetBehaviour, expire : Duration) : Unit = ???

  override def getStringList(key : String) : Iterable[String] = ???

  override def getListLength(key : String) : Option[Int] = ???

  override def setObject(key : String, value : Option[Any], whenSet : StringApi.SetBehaviour, expire : Duration) : Unit = ???

  override def getObject(key : String) : Option[Any] = ???

  override def setObjectToString(key : String, value : Option[Any], whenSet : StringApi.SetBehaviour, expire : Duration) : Unit = ???

  override def getObjectAsString(key : String) : Option[String] = ???

  override def getObjectAs[T](key : String) : Option[T] = ???
}
