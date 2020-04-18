package shared.com.ortb.webapi.traits

import io.circe.{Decoder, Encoder}

trait RestWebApiJson[TTable, TRow, TKey] {

  def fromJsonToEntities(entitiesJsonString : Option[String])(implicit decoder: Decoder[Iterable[TRow]]) : Option[]

  def fromEntityToJson(entity : TRow)(implicit decoder: Decoder[TRow]) : String

  def fromEntityToJson[T](item : T)(implicit encoder: Encoder[T]) : Option[String]

  def toJson[T](items : Iterable[T])
    (implicit encoder: Encoder[Iterable[T]]) : Option[String]
}
