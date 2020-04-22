package shared.io.traits.jsonParse


import com.fasterxml.jackson.annotation.JsonValue
import io.circe.{ Decoder, _ }
import io.circe.generic.codec.DerivedAsObjectCodec
import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import io.circe.generic.semiauto.{ deriveDecoder, _ }
import play.api.libs.json
import play.api.libs.json.{ JsValue, Json }
import shapeless.Lazy
import shared.com.ortb.implicits.implementations.CirceJsonSupport
import spray.json.JsArray

import scala.reflect.ClassTag

class JsonCirceDefaultEncoders[T]
(
  implicit val decodeCodec : DerivedAsObjectCodec[T],
  implicit val decoder : Lazy[DerivedDecoder[T]],
//  implicit val decoderIterable : Lazy[DerivedDecoder[Iterable[T]]],
  implicit val classTag : ClassTag[T],
  implicit val decodeDeviceDecode : ClassTag[T],
  val encoder : Lazy[DerivedAsObjectEncoder[T]],
  val decodeListCodec : DerivedAsObjectCodec[List[T]]
) extends CirceJsonSupport {

  implicit def defaultCodec : Codec.AsObject[T] = deriveCodec[T](decodeCodec)

  implicit def defaultListCodec : Codec.AsObject[List[T]] =
    deriveCodec[List[T]](decodeListCodec)

  implicit def defaultDecoder : Decoder[T] = deriveDecoder[T]

  //  implicit def defaultArrayDecoder : Decoder[Array[T]] = deriveDecoder[Array[T]](decoderArray)
//  implicit def defaultArrayDecoder : Decoder[Iterable[T]] = deriveDecoder[Iterable[T]](decoderIterable)

  implicit def defaultListDecoder : Decoder[List[T]] = deriveDecoder[List[T]]

  implicit def defaultEncoder : Encoder[T] = deriveEncoder[T]

  implicit def defaultListEncoder : Encoder[List[T]] = deriveEncoder[List[T]]

  def toJson(entities : Iterable[T]) : JsValue = {
    json.Json.parse(toJsonString(entities))
  }

  def toJsonString(entities : Iterable[T]) : String = {
    entities.map(entity => defaultEncoder(entity)).mkString("[", ",", "]")
  }
}
