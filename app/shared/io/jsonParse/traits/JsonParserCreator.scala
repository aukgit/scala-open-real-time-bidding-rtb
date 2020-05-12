package shared.io.jsonParse.traits

import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import shapeless.Lazy
import shared.io.jsonParse.implementations.BasicJsonEncoderImplementation

trait JsonParserCreator {
  def getBasicJsonEncoder[T](
    implicit decoder : Lazy[DerivedDecoder[T]],
    encoder : Lazy[DerivedAsObjectEncoder[T]]) : BasicJsonEncoder[T] =
    new BasicJsonEncoderImplementation[T]()
}
