package shared.io.jsonParse.implementations

import io.circe.Encoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import io.circe.generic.semiauto.deriveEncoder
import shapeless.Lazy
import shared.io.jsonParse.traits.JsonEncoders

class JsonEncodersImplementation[T]
(implicit val encoder : Lazy[DerivedAsObjectEncoder[T]])
  extends JsonEncoders[T] {

  implicit def defaultEncoder : Encoder[T] = deriveEncoder[T]

  implicit def defaultListEncoder : Encoder[List[T]] = deriveEncoder[List[T]]
}
