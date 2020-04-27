package shared.io.redis

import com.google.inject.Inject
import com.redis.RedisClient
import com.redis.api.StringApi.{ Always, SetBehaviour }
import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import shapeless.Lazy
import shared.com.ortb.model.config.DomainPortModel
import shared.io.helpers.EmptyValidateHelper
import shared.io.jsonParse.implementations.BasicJsonEncoderImplementation
import shared.io.jsonParse.traits.{ BasicJsonEncoder, CommonJsonParsingMechanism }
import shared.io.loggers.AppLogger
import shared.io.redis.traits.RedisClientCore

import scala.concurrent.duration.Duration
import scala.reflect.ClassTag


class CommonJsonParsingMechanismForRedisImplementation @Inject()(
  redisClientCore : RedisClientCore
)
  extends CommonJsonParsingMechanism with RedisClientCore {

  def getBasicJsonEncoder[T](
    implicit decoder : Lazy[DerivedDecoder[T]],
    encoder : Lazy[DerivedAsObjectEncoder[T]]) : BasicJsonEncoder[T] =
    new BasicJsonEncoderImplementation[T]()

  override def setObjectAsJson[T](
    key : String,
    value : Option[T],
    whenSet : SetBehaviour = Always,
    expire : Duration = null)
    (
      implicit decoder : Lazy[DerivedDecoder[T]],
      encoder : Lazy[DerivedAsObjectEncoder[T]])  : Unit = {
    try {
      if (EmptyValidateHelper.isEmpty(value, Some(s"Key: $key, value given is empty."))) {
        return
      }

      val jsonParser = getBasicJsonEncoder[T]
      val toJson = jsonParser.getJsonGenericParser.toJsonString(value)
      redisClientCore.redisClient.set(key, toJson.get, whenSet, expire)
    }
    catch {
      case e : Exception =>
        AppLogger.errorCaptureAndThrow(e, key)
    }
  }

  override def getObjectFromJsonAs[T](key : String)(implicit classTag : ClassTag[T]) : Option[T] = ???

  override def setIterableObjectsAsJson[T](key : String, value : Iterable[T],
                                           whenSet : SetBehaviour = Always,
                                           expire : Duration = null)(implicit classTag : ClassTag[T]) : Unit = ???

  override def getIterableObjectsAs[T](key : String)(implicit classTag : ClassTag[T]) : Iterable[T] = ???

  override val redisClient : RedisClient =
    redisClientCore.redisClient
  override val redisServerConfigurationInfo : DomainPortModel =
    redisClientCore.redisServerConfigurationInfo
}
