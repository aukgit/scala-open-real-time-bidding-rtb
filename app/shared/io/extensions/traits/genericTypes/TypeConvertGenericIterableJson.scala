package shared.io.extensions.traits.genericTypes

import shared.io.extensions.TypeConvertExtensions._
import io.circe.Json
import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import shapeless.Lazy
import shared.io.jsonParse.implementations.BasicJsonEncoderImplementation
import shared.io.jsonParse.traits.{ BasicJsonEncoder, GenericJsonParser }
import shared.io.loggers.AppLogger

trait TypeConvertGenericIterableJson[T]
  extends TypeConvertGenericIterableLogToDatabase[T] {
  this : TypeConvertGenericIterable[T] =>

  def toJsons(
               implicit decoder : Lazy[DerivedDecoder[T]],
               encoder : Lazy[DerivedAsObjectEncoder[T]]) : Option[Iterable[Json]] = {
    throwOnNullOrNoneOrNil()

    jsonGenericParser(decoder, encoder)
      .fromModelsToJsonObjects(toSome)
  }

  def jsonGenericParser(
                         implicit decoder : Lazy[DerivedDecoder[T]],
                         encoder : Lazy[DerivedAsObjectEncoder[T]]) : GenericJsonParser[T] =
    basicJsonEncoder(decoder, encoder).genericJsonParser

  def basicJsonEncoder(
                        implicit decoder : Lazy[DerivedDecoder[T]],
                        encoder : Lazy[DerivedAsObjectEncoder[T]]) : BasicJsonEncoder[T] =
    new BasicJsonEncoderImplementation[T]()(decoder, encoder)

  def toJsonString(implicit decoder : Lazy[DerivedDecoder[T]],
                   encoder : Lazy[DerivedAsObjectEncoder[T]]) : String = {
    throwOnNullOrNoneOrNil()
    val mayJsonString =
      jsonGenericParser(decoder, encoder)
        .fromModelsToJsonString(toSome)

    mayJsonString.getDefinedOrEmptyString
  }

  def logAsJson(message : String = "")(
    implicit decoder : Lazy[DerivedDecoder[T]],
    encoder : Lazy[DerivedAsObjectEncoder[T]]) : Unit = {
    val json = toPrettyJsonString
    AppLogger.log(json, message)
  }

  def toPrettyJsonString(implicit decoder : Lazy[DerivedDecoder[T]],
                         encoder : Lazy[DerivedAsObjectEncoder[T]]) : String = {
    throwOnNullOrNoneOrNil()
    val maybeJson = jsonGenericParser(decoder, encoder)
      .toJsonStringPrettyFormatForModels(toSome)

    maybeJson.getDefinedOrEmptyString
  }
}
