package shared.io.jsonParse.traits

import io.circe.Encoder

trait JsonEncoders[T] extends CirceJsonSupport {
  implicit def defaultEncoder : Encoder[T]

  implicit def defaultListEncoder : Encoder[List[T]]
}
