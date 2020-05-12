package shared.io.redis.implementations

import com.google.inject.Inject
import com.redis.RedisClient
import com.redis.api.StringApi.{ Always, SetBehaviour }
import io.circe.Json
import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import shapeless.Lazy
import shared.com.ortb.model.config.DomainPortModel
import shared.com.ortb.model.results.DualResultModel
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
      encoder : Lazy[DerivedAsObjectEncoder[T]]) :  Option[Json] = {
    try {
      if (EmptyValidateHelper.isEmpty(value, Some(s"Key: $key, value given is empty."))) {
        return None
      }

      val jsonParser = getBasicJsonEncoder[T]
      val toJson = jsonParser.getJsonGenericParser.toJsonObject(value)

      if(EmptyValidateHelper.isDefined(toJson)){
        redisClient.set(key, toJson.get.noSpaces, whenSet, expire)
        return toJson
      }
    }
    catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, key)
    }

    None
  }

  override def getObjectFromJsonAs[T](key : String)(
    implicit decoder : Lazy[DerivedDecoder[T]],
    encoder : Lazy[DerivedAsObjectEncoder[T]]) : Option[T] = {
    try {
      val value = redisClient.get(key)
      val jsonParser = getBasicJsonEncoder[T]
      return jsonParser.getJsonGenericParser.toModel(value)
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
    encoder : Lazy[DerivedAsObjectEncoder[T]]) : Option[DualResultModel[Iterable[Json], String]] = {
    try {
      if (EmptyValidateHelper.isItemsEmpty(Some(items), Some(
        s"[setIterableObjectsAsJson] -> Key: $key, value given is empty."))) {
        return None
      }

      val jsonParser = getBasicJsonEncoder[T]
      val parser = jsonParser
        .getJsonGenericParser
      val toJsons = parser
        .fromModelsToJsonObjects(Some(items))

      if(EmptyValidateHelper.hasAnyItem(toJsons)){
        val jsonString =parser.fromJsonsToJsonString(toJsons)
        redisClient.set(key, jsonString.get, whenSet, expire)

        return Some(DualResultModel[Iterable[Json], String](
          toJsons,
          jsonString))
      }
    }
    catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, key)
    }

    None
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
