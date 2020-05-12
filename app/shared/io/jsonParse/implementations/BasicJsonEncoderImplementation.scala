package shared.io.jsonParse.implementations

import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import io.circe.generic.semiauto.{ deriveDecoder, deriveEncoder }
import io.circe.{ Decoder, Encoder }
import shapeless.Lazy
import shared.io.jsonParse.traits.BasicJsonEncoder

class BasicJsonEncoderImplementation[T]
(
  implicit val decoder : Lazy[DerivedDecoder[T]],
  implicit val encoder : Lazy[DerivedAsObjectEncoder[T]]
)
  extends BasicJsonEncoder[T] {
  def getEncoder : Encoder[T] = deriveEncoder[T](encoder)

  def getDecoder : Decoder[T] = deriveDecoder[T](decoder)
}
