package shared.io.jsonParse.traits

import io.circe.generic.semiauto.{ deriveDecoder, deriveEncoder }
import io.circe.{ Decoder, Encoder }

import scala.reflect.ClassTag

class BasicJsonEncoderImplementation[T](implicit classTag : ClassTag[T]) extends BasicJsonEncoder[T] {
  def getEncoder : Encoder[T] = deriveEncoder[T]

  def getDecoder : Decoder[T] = deriveDecoder[T]
}
