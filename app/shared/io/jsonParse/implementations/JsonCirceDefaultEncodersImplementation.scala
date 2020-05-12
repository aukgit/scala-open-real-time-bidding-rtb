package shared.io.jsonParse.implementations

import io.circe.{ Decoder, _ }
import io.circe.derivation._
import io.circe.generic.codec.DerivedAsObjectCodec
import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder

import play.api.libs.json
import play.api.libs.json.JsValue
import shapeless.Lazy
import shared.io.jsonParse.traits.JsonCirceDefaultEncoders

import scala.reflect.ClassTag

class JsonCirceDefaultEncodersImplementation[T](
   implicit val decodeCodec : DerivedAsObjectCodec[T],
   implicit val decoder : Lazy[DerivedDecoder[T]],
   implicit val classTag : ClassTag[T],
   implicit val decodeDeviceDecode : ClassTag[T],
   val encoder : Lazy[DerivedAsObjectEncoder[T]],
   val decodeListCodec : DerivedAsObjectCodec[List[T]]
 ) extends JsonCirceDefaultEncoders[T] {

  import io.circe.generic.semiauto.{ deriveDecoder, _ }

  def defaultCodec : Codec.AsObject[T] = deriveCodec[T](decodeCodec)

  def defaultListCodec : Codec.AsObject[List[T]] =
    deriveCodec[List[T]](decodeListCodec)

  def defaultListDecoder : Decoder[List[T]] = deriveDecoder[List[T]]

  def defaultListEncoder : Encoder[List[T]] = deriveEncoder[List[T]]

  def toJson(entities : Iterable[T]) : JsValue = {
    json.Json.parse(toJsonString(entities))
  }

  def toJsonString(entities : Iterable[T],
                   additionalAnnotationForItems : String = null) : String = {
    getJsonGenericParser.toJsonString(entities, additionalAnnotationForItems)
  }

  def toIterableJson(entities : Iterable[T]) : Iterable[Json] = {
    entities.map(entity => defaultEncoder(entity))
  }

  override def getEncoder : Encoder[T] = defaultEncoder

  def defaultEncoder : Encoder[T] = deriveEncoder[T]

  override def getDecoder : Decoder[T] = defaultDecoder

  def defaultDecoder : Decoder[T] = deriveDecoder[T]
}
