package shared.io.redis.implementations

import com.google.inject.Inject
import com.redis.api.StringApi
import com.redis.serialization.Parse.Implicits.parseByteArray
import shared.io.helpers.{ EmptyValidateHelper, SerializingHelper }
import shared.io.loggers.AppLogger
import shared.io.redis.traits.{ RedisClientCoreProperties, RedisKeyValueParser }

import scala.concurrent.duration.Duration

class RedisKeyValueParserImplementation @Inject()(
  val redisClientCore : RedisClientCoreProperties
)
  extends RedisCommonJsonParsingMechanismImplementation(redisClientCore)
    with RedisKeyValueParser
    with RedisClientCoreProperties {

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

  override def setStringList(listKey : String, items : Iterable[String]) : Unit = {
    EmptyValidateHelper.throwOnNullOrNone(listKey)

    try {
      if (EmptyValidateHelper.isItemsEmpty(Some(items))) {
        return
      }

      items.foreach(item => redisClient.lpush(listKey, item))
    } catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, s"Having issue during setStringList->lpush key: $listKey")
    }
  }

  override def getStringList(listKey : String) : Option[List[Option[String]]] = {
    try {
      val rawLength = redisClient.llen(listKey)
      if (EmptyValidateHelper.isDefined(rawLength)) {
        val length = rawLength.get.asInstanceOf[Int]
        return redisClient.lrange[String](listKey, 0, length)
      }
    } catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, s"Having issue during getListAs->lpush key: $listKey")
    }

    None
  }

  override def getListLength(listKey : String) : Option[Int] = {
    val rawLength = redisClient.llen(listKey)

    if (EmptyValidateHelper.isDefined(rawLength)) {
      return Some(rawLength.get.asInstanceOf[Int])
    }

    None
  }

  override def getDeserializeObjectAs[T](key : String) : Option[T] = {
    try {
      val item = redisClient.get[Array[Byte]](key)
      val onEmptyMessage = Some(s"Key : $key doesn't have any redis cache value as Array[Byte]")
      if (EmptyValidateHelper.isEmpty(item, onEmptyMessage)) {
        return None
      }

      val unWrapItem = item.get

      return SerializingHelper.byteArrayToObjectAs[T](unWrapItem)
    } catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, s"Having issue during [getDeserializeObjectAs] key: $key")
    }

    None
  }

  override def getSerializedObjectsAs[T](key : String) : Option[List[T]] = {
    try {
      val item = redisClient.get[Array[Byte]](key)
      val onEmptyMessage = Some(s"Key : $key doesn't have any redis cache value as Array[Byte]")
      if (EmptyValidateHelper.isEmpty(item, onEmptyMessage)) {
        return None
      }

      val unWrapItem = item.get

      return SerializingHelper.byteArrayToObjectAs[List[T]](unWrapItem)
    } catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, s"Having issue during [getDeserializeObjectAs] key: $key")
    }

    None
  }

  override def getObject(key : String) : Option[Any] = {
    try {
      return redisClient.get(key)
    } catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, s"Having issue during getObject->get key: $key")
    }

    None
  }

  override def setSerializedObjectToString(
    key : String,
    value : Option[Any],
    whenSet : StringApi.SetBehaviour,
    expire : Duration) : Unit = {
    try {
      if (EmptyValidateHelper.isEmpty(value)) {
        redisClient.set(key, null, whenSet, expire)

        return
      }

      val bytes = SerializingHelper.toBytesArray(value.get)
      redisClient.set(key, bytes.get, whenSet, expire)
    } catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, s"Having issue during setSerializedObjectToString key: $key")
    }

    None
  }

  override def setSerializedObjectsToString(
    key : String,
    items : Option[List[Any]],
    whenSet : StringApi.SetBehaviour,
    expire : Duration) : Unit = {
    try {
      if (EmptyValidateHelper.isEmpty(items)) {
        redisClient.set(key, None, whenSet, expire)

        return
      }

      val bytes = SerializingHelper.toBytesArray(items.get)
      redisClient.set(key, bytes.get, whenSet, expire)
    } catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, s"Having issue during setSerializedObjectsToString key: $key")
    }

    None
  }
}
