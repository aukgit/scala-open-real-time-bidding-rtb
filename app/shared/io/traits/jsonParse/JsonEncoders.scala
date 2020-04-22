package shared.io.traits.jsonParse

import io.circe.Encoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import io.circe.generic.semiauto.deriveEncoder
import shapeless.Lazy
import shared.com.ortb.implicits.implementations.CirceJsonSupport

class JsonEncoders[T]
(implicit val encoder : Lazy[DerivedAsObjectEncoder[T]])
  extends CirceJsonSupport {

  implicit def defaultEncoder : Encoder[T] = deriveEncoder[T]

  implicit def defaultListEncoder : Encoder[List[T]] = deriveEncoder[List[T]]
}
