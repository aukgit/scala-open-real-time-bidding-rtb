package shared.io.extensions.traits.primitiveTypes

import io.circe.Json
import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.encoding.DerivedAsObjectEncoder
import shapeless.Lazy
import shared.io.helpers.EmptyValidateHelper
import shared.io.jsonParse.implementations.BasicJsonEncoderImplementation
import shared.io.jsonParse.traits.{ BasicJsonEncoder, GenericJsonParser }

trait TypeConvertGenericIterable[T] {
  lazy val isEmpty : Boolean = !hasItem
  lazy val hasItem : Boolean = EmptyValidateHelper.hasAnyItemDirect(anyItems)
  lazy val toSome : Option[Iterable[T]] = Some(anyItems)
  lazy val toOption : Option[Iterable[T]] = Some(anyItems)
  lazy val toMaybe : Option[Iterable[T]] = Some(anyItems)
  lazy val toCsv : String = anyItems.mkString(",")
  protected val anyItems : Iterable[T]

  def toJoinString(separator : String) : String = anyItems.mkString(separator)

  def toJoinString(start : String, separator : String, end : String) : String =
    anyItems.mkString(start, separator, end)

  def toJsons(
               implicit decoder : Lazy[DerivedDecoder[T]],
               encoder : Lazy[DerivedAsObjectEncoder[T]]) : Option[Iterable[Json]] = {
    throwOnNullOrNoneOrNil()
    jsonGenericParser(decoder, encoder).fromModelsToJsonObjects(toSome)
  }

  def toJsonString(implicit decoder : Lazy[DerivedDecoder[T]],
                   encoder : Lazy[DerivedAsObjectEncoder[T]]) : Option[String] = {
    throwOnNullOrNoneOrNil()
    jsonGenericParser(decoder, encoder).fromModelsToJsonString(toSome)
  }

  def jsonGenericParser(
                         implicit decoder : Lazy[DerivedDecoder[T]],
                         encoder : Lazy[DerivedAsObjectEncoder[T]]) : GenericJsonParser[T] =
    basicJsonEncoder(decoder, encoder).getJsonGenericParser

  def basicJsonEncoder(
                        implicit decoder : Lazy[DerivedDecoder[T]],
                        encoder : Lazy[DerivedAsObjectEncoder[T]]) : BasicJsonEncoder[T] =
    new BasicJsonEncoderImplementation[T]()(decoder, encoder)

  private def throwOnNullOrNoneOrNil() : Unit = {
    EmptyValidateHelper.throwOnNullOrNoneOrNil(
      anyItems,
      Some(s"Undefined object given for json parsing."))
  }

  def toPrettyJsonString(
                          implicit decoder : Lazy[DerivedDecoder[T]],
                          encoder : Lazy[DerivedAsObjectEncoder[T]]) : Option[String] = {
    throwOnNullOrNoneOrNil()
    jsonGenericParser(decoder, encoder).toJsonStringPrettyFormatForModels(
      toSome)
  }
}
