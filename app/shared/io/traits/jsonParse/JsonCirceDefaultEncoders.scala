package shared.io.traits.jsonParse

import io.circe.generic.auto._ // must to import.
import io.circe.generic.codec.DerivedAsObjectCodec
import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import io.circe.generic.semiauto._
import io.circe._
import shapeless.Lazy

class JsonCirceDefaultEncoders[T]
(
  implicit val decodeCodec : DerivedAsObjectCodec[T],
  implicit val decoder : Lazy[DerivedDecoder[T]],
  val encoder : Lazy[DerivedAsObjectEncoder[T]],
  val decodeListCodec : DerivedAsObjectCodec[List[T]]){

  implicit def defaultCodec : Codec.AsObject[T] = deriveCodec[T](decodeCodec)

  implicit def defaultListCodec : Codec.AsObject[List[T]] =
    deriveCodec[List[T]](decodeListCodec)

  implicit def defaultDecoder : Decoder[T] = deriveDecoder[T]

  implicit def defaultListDecoder : Decoder[List[T]] = deriveDecoder[List[T]]

  implicit def defaultEncoder : Encoder[T] = deriveEncoder[T]

  implicit def defaultListEncoder : Encoder[List[T]] = deriveEncoder[List[T]]
}
