package shared.io.jsonParse.implementations

import io.circe.Decoder
import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.semiauto.deriveDecoder
import shapeless.Lazy
import shared.io.jsonParse.traits.JsonDecoders

class JsonDecodersImplementation[T]
(implicit val decoder : Lazy[DerivedDecoder[T]])
  extends JsonDecoders[T] {

  implicit def defaultDecoder : Decoder[T] = deriveDecoder[T]

  implicit def defaultListDecoder : Decoder[List[T]] = deriveDecoder[List[T]]
}