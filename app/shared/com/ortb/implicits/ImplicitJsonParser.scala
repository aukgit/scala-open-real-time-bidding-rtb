package shared.com.ortb.implicits

import cats.kernel.Hash
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

trait ImplicitJsonParser[T] {
  implicit val defaultEncoder : Encoder[T] = deriveEncoder
  implicit val defaultDecoder : Decoder[T] = deriveDecoder
}
