package shared.io.redis.implementations

import com.google.inject.Inject
import com.redis.RedisClient
import com.redis.api.StringApi.{ Always, SetBehaviour }
import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import shapeless.Lazy
import shared.com.ortb.model.config.DomainPortModel
import shared.io.helpers.EmptyValidateHelper
import shared.io.jsonParse.traits.{ CommonJsonParsingMechanism, JsonParserCreator }
import shared.io.loggers.AppLogger
import shared.io.redis.traits.RedisClientCorePropertiesContracts

import scala.concurrent.duration.Duration

class RedisCommonJsonParsingMechanismImplementation @Inject()(
  redisClientCore : RedisClientCorePropertiesContracts
)
  extends CommonJsonParsingMechanism
    with RedisClientCorePropertiesContracts
    with JsonParserCreator {

  override val redisClient : RedisClient =
    redisClientCore.redisClient
  override val redisServerConfigurationInfo : DomainPortModel =
    redisClientCore.redisServerConfigurationInfo

  override def setObjectAsJson[T](
    key : String,
    value : Option[T],
    whenSet : SetBehaviour = Always,
    expire : Duration = null)
    (
      implicit decoder : Lazy[DerivedDecoder[T]],
      encoder : Lazy[DerivedAsObjectEncoder[T]]) : Unit = {
    try {
      if (EmptyValidateHelper.isEmpty(value, Some(s"Key: $key, value given is empty."))) {
        return
      }

      val jsonParser = getBasicJsonEncoder[T]
      val toJson = jsonParser.getJsonGenericParser.toJsonString(value)
      redisClient.set(key, toJson.get, whenSet, expire)
    }
    catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, key)
    }
  }

  override def getObjectFromJsonAs[T](key : String)(
    implicit decoder : Lazy[DerivedDecoder[T]],
    encoder : Lazy[DerivedAsObjectEncoder[T]]) : Option[T] = {
    try {
      val value = redisClient.get(key)
      val jsonParser = getBasicJsonEncoder[T]
      jsonParser.getJsonGenericParser.toModel(value)
    }
    catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, key)
    }

    None
  }

  override def setIterableObjectsAsJson[T](
    key : String,
    items : Iterable[T],
    whenSet : SetBehaviour = Always,
    expire : Duration = null)(
    implicit decoder : Lazy[DerivedDecoder[T]],
    encoder : Lazy[DerivedAsObjectEncoder[T]]) : Unit = {
    try {
      if (EmptyValidateHelper.isItemsEmpty(Some(items), Some(s"[setIterableObjectsAsJson] -> Key: $key, value given is empty."))) {
        return
      }

      val jsonParser = getBasicJsonEncoder[T]
      val toJson = jsonParser.getJsonGenericParser.fromModelsToJsonString(Some(items))
      redisClient.set(key, toJson.get, whenSet, expire)
    }
    catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, key)
    }
  }

  def getIterableObjectsAs[T](key : String)(
    implicit decoder : Lazy[DerivedDecoder[T]],
    encoder : Lazy[DerivedAsObjectEncoder[T]]) : Option[Iterable[T]] = {
    try {
      //noinspection DuplicatedCode
      val valuesAsJsonString = redisClient.get(key)
      val emptyMessage = Some(s"[getIterableObjectsAs] -> Key: ${ key }, received empty value")
      if (EmptyValidateHelper.isEmptyOptionString(valuesAsJsonString, emptyMessage)) {
        return None
      }

      val jsonParser = getBasicJsonEncoder[T]
      val models = jsonParser.getJsonGenericParser
        .toModels(valuesAsJsonString)

      return models
    }
    catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, key)
    }

    None
  }
}
