package shared.io.jsonParse.implementations

import io.circe.Codec
import io.circe.generic.codec.DerivedAsObjectCodec
import io.circe.generic.semiauto.deriveCodec
import shared.io.jsonParse.traits.JsonCodec

class JsonCodecImplementation[T](
  implicit val decodeCodec : DerivedAsObjectCodec[T],
  val decodeListCodec      : DerivedAsObjectCodec[List[T]])
  extends JsonCodec[T] {

  implicit def defaultCodec : Codec.AsObject[T] = deriveCodec[T](decodeCodec)

  implicit def defaultListCodec : Codec.AsObject[List[T]] =
    deriveCodec[List[T]](decodeListCodec)
}
