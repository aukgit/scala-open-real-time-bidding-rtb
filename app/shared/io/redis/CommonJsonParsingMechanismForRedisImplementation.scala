package shared.io.redis

import com.google.inject.Inject
import shared.io.helpers.EmptyValidateHelper
import shared.io.jsonParse.traits.{ CommonJsonParsingMechanism, GenericJsonParser }
import shared.io.redis.traits.RedisClientCore

import scala.reflect.ClassTag


class CommonJsonParsingMechanismForRedisImplementation @Inject()(
  redisClientCore : RedisClientCore
)
  extends CommonJsonParsingMechanism {
  override def setObjectAsJson[T](
    key : String,
    value : Option[T])
    (implicit classTag : ClassTag[T]) : Unit = {
    try {
      if(EmptyValidateHelper.isEmpty(value, Some(s"Key: $key, value given is empty."))){
        return
      }

      val valueAs = value.get
      val jsonParser = new GenericJsonParser {}

      redisClientCore.redisClient.se
    }
  }

  override def getObjectFromJsonAs[T](key : String)(implicit classTag : ClassTag[T]) : Option[T] = ???

  override def setIterableObjectsAsJson[T](key : String, value : Iterable[T])(implicit classTag : ClassTag[T]) : Unit = ???

  override def getIterableObjectsAs[T](key : String)(implicit classTag : ClassTag[T]) : Iterable[T] = ???
}
