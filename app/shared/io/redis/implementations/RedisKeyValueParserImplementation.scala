package shared.io.redis.implementations

import com.google.inject.Inject
import com.redis.RedisClient
import com.redis.api.StringApi
import shared.com.ortb.model.config.DomainPortModel
import shared.io.helpers.{ EmptyValidateHelper, NumberHelper }
import shared.io.loggers.AppLogger
import shared.io.redis.traits.{ RedisClientCoreProperties, RedisKeyValueParser }

import scala.concurrent.duration.Duration

class RedisKeyValueParserImplementation @Inject()(
  val redisClientCore : RedisClientCoreProperties
)
  extends RedisCommonJsonParsingMechanismImplementation(redisClientCore)
    with RedisKeyValueParser
    with RedisClientCoreProperties {

  override lazy val redisClient : RedisClient =
    redisClientCore.redisClient
  override lazy val redisServerConfigurationInfo : DomainPortModel =
    redisClientCore.redisServerConfigurationInfo

  override def set(key : String, value : Any, whenSet : StringApi.SetBehaviour, expire : Duration) : Unit = {
    EmptyValidateHelper.throwOnNullOrNone(key)

    try {
      redisClient.set(key, value, whenSet, expire)
    } catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, s"Having issue during setting key: $key")
    }
  }

  override def appendItemToList(listKey : String, value : Any) : Unit = {
    EmptyValidateHelper.throwOnNullOrNone(listKey)

    try {
      redisClient.lpush(listKey, value)
    } catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, s"Having issue during appendItemToList->lpush key: $listKey")
    }
  }

  override def setAnyList(listKey : String, items : Iterable[Any]) : Unit = {
    EmptyValidateHelper.throwOnNullOrNone(listKey)

    try {
      if (EmptyValidateHelper.isItemsEmpty(Some(items))) {
        return
      }

      items.foreach(item => redisClient.lpush(listKey, item))
    } catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, s"Having issue during appendItemToList->lpush key: $listKey")
    }
  }

  override def removeKeys(keys : String*) : Unit = {
    try {
      redisClient.del(keys)
    } catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, s"Having issue during removeKeys->del key: $keys")
    }
  }

  override def clearList(listKey : String) : Unit = {
    EmptyValidateHelper.throwOnNullOrNone(listKey)

    try {
      redisClient.del(listKey)
    } catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, s"Having issue during clearList->del key: $listKey")
    }
  }

  override def setList[T](
    listKey : String,
    items : Iterable[T]) : Unit = {
    EmptyValidateHelper.throwOnNullOrNone(listKey)

    try {
      if(EmptyValidateHelper.isItemsEmpty(Some(items))){
        return
      }

      items.foreach(item => redisClient.lpush(listKey, item))
    } catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, s"Having issue during setList->lpush key: $listKey")
    }
  }

  override def getStringList(listKey : String) : Option[List[Option[String]]] = {
    try {
      val rawLength = redisClient.llen(listKey)
      if(EmptyValidateHelper.isDefined(rawLength)){
        val length = rawLength.get.asInstanceOf[Int]
        return redisClient.lrange(listKey, 0, length)
      }
    } catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, s"Having issue during getListAs->lpush key: $listKey")
    }

    None
  }

  override def getListLength(listKey : String) : Option[Int] = ???

  override def setObject(key : String, value : Option[Any], whenSet : StringApi.SetBehaviour, expire : Duration) : Unit = ???

  override def getSerializedObjectAs[T](key : String) : Option[T] = ???

  override def getSerializedObjectsAs[T](key : String) : Option[Iterable[T]] = ???

  override def getObject(key : String) : Option[Any] = ???

  override def setSerializedObjectToString(key : String, value : Option[Any], whenSet : StringApi.SetBehaviour, expire : Duration) : Unit = ???

  override def setSerializedObjectsToString(key : String, value : Option[Iterable[Any]], whenSet : StringApi.SetBehaviour, expire : Duration) : Unit = ???
}
