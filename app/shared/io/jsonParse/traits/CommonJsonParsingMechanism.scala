package shared.io.jsonParse.traits

import com.redis.api.StringApi.{ Always, SetBehaviour }
import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import shapeless.Lazy

import scala.concurrent.duration.Duration

trait CommonJsonParsingMechanism {
  def setObjectAsJson[T](
    key : String,
    value : Option[T],
    whenSet : SetBehaviour = Always,
    expire : Duration = null
  )(
    implicit decoder : Lazy[DerivedDecoder[T]],
    encoder : Lazy[DerivedAsObjectEncoder[T]]) : Unit

  def getObjectFromJsonAs[T](key : String)(
    implicit decoder : Lazy[DerivedDecoder[T]],
    encoder : Lazy[DerivedAsObjectEncoder[T]]) : Option[T]

  def setIterableObjectsAsJson[T](
    key : String,
    items : Iterable[T],
    whenSet : SetBehaviour = Always,
    expire : Duration = null)(
    implicit decoder : Lazy[DerivedDecoder[T]],
    encoder : Lazy[DerivedAsObjectEncoder[T]]) : Unit

  def getIterableObjectsAs[T](key : String)(
    implicit decoder : Lazy[DerivedDecoder[T]],
    encoder : Lazy[DerivedAsObjectEncoder[T]]) : Option[Iterable[T]]
}
