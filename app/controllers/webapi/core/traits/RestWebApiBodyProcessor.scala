package controllers.webapi.core.traits

trait RestWebApiBodyProcessor[TTable, TRow, TKey] {

  def bodyToString(request : Request[AnyContent]) : String

  def bodyRequestToEntity(request : Request[AnyContent]) :
  Option[WebApiEntityResponseWrapper[TRow, TKey]]

  def bodyRequestToEntities(request : Request[AnyContent]) : Option[WebApiEntitiesResponseWrapper[TRow, TKey]]
}
