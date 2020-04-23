package shared.io.jsonParse

import io.circe.Decoder
import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.semiauto.deriveDecoder
import shapeless.Lazy
import shared.io.jsonParse.traits.CirceJsonSupport

class JsonDecoders[T]
(implicit val decoder : Lazy[DerivedDecoder[T]])
  extends CirceJsonSupport {

  implicit def defaultDecoder : Decoder[T] = deriveDecoder[T]

  implicit def defaultListDecoder : Decoder[List[T]] = deriveDecoder[List[T]]
}