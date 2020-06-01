package shared.io.extensions.traits.primitiveTypes

import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import shapeless.Lazy
import shared.io.jsonParse.implementations.BasicJsonEncoderImplementation

trait TypeConvertStringJson extends StringExtensionEssentials {
  /**
   * Parse a json to object
   *
   * @param decoder
   * @param encoder
   * @tparam T2
   *
   * @return
   */
  def asFromJson[T2](
                      implicit decoder : Lazy[DerivedDecoder[T2]],
                      encoder : Lazy[DerivedAsObjectEncoder[T2]]) : T2 = {
    val basicEncoder = new BasicJsonEncoderImplementation[T2]()(decoder, encoder)
    basicEncoder.genericJsonParser.toModelDirect(s)
  }

  def asObjectsFromJson[T2](
                             implicit decoder : Lazy[DerivedDecoder[T2]],
                             encoder : Lazy[DerivedAsObjectEncoder[T2]]) : Iterable[T2] = {
    val basicEncoder = new BasicJsonEncoderImplementation[T2]()(decoder, encoder)
    basicEncoder.genericJsonParser.toModelsDirect(s)
  }

  def asObjectFromJson[T2](
                            implicit decoder : Lazy[DerivedDecoder[T2]],
                            encoder : Lazy[DerivedAsObjectEncoder[T2]]) : T2 = {
    val basicEncoder = new BasicJsonEncoderImplementation[T2]()(decoder, encoder)
    basicEncoder.genericJsonParser.toModelDirect(s)
  }
}
