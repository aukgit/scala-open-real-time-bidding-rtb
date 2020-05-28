package shared.io.extensions.traits.genericTypes

import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import io.circe.{ Encoder, Json }
import shapeless.Lazy
import shared.com.repository.traits.FutureToRegular
import shared.io.helpers.JsonHelper
import shared.io.jsonParse.implementations.BasicJsonEncoderImplementation
import shared.io.jsonParse.traits.{ BasicJsonEncoder, GenericJsonParser }

import scala.concurrent.Future
import scala.concurrent.duration.Duration


trait TypeConvertGenericJson[T] {
  protected val anyItem : T

  def jsonGenericParser(
                         implicit decoder : Lazy[DerivedDecoder[T]],
                         encoder : Lazy[DerivedAsObjectEncoder[T]]) : GenericJsonParser[T] =
    basicJsonEncoder(decoder, encoder).genericJsonParser

  def basicJsonEncoder(
                        implicit decoder : Lazy[DerivedDecoder[T]],
                        encoder : Lazy[DerivedAsObjectEncoder[T]]) : BasicJsonEncoder[T] =
    new BasicJsonEncoderImplementation[T]()(decoder, encoder)

  def toJsonString(implicit encoder : Encoder[T]) : String =
    if (toJson.isDefined) toJson.get.noSpaces else ""

  def toJson(implicit encoder : Encoder[T]) : Option[Json] =
    JsonHelper.toJson(anyItem)
}
