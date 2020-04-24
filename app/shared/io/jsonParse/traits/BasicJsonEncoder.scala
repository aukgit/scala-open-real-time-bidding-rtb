package shared.io.jsonParse.traits

import io.circe.{ Decoder, Encoder }
import shared.io.jsonParse.implementations.GenericJsonParserImplementation

trait BasicJsonEncoder[T] {
  def getEncoder : Encoder[T]

  def getDecoder : Decoder[T]

  def getJsonGenericParser : GenericJsonParser[T] = new GenericJsonParserImplementation[T](this)
}
