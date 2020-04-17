package shared.com.ortb.webapi.traits

trait RestWebApiJson[TTable, TRow, TKey] {

  def toJsonFrom(entity : Iterable[TRow]) : String

  def toEntityJson(entity : TRow) : String

  def toJson[T](item : T) : String

  def toJson[T](items : Iterable[T]) : String
}
