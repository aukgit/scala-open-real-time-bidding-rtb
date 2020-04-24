package controllers.webapi.core.traits

trait RestWebApiBodyProcessorImplementation[TTable, TRow, TKey]
  extends RestWebApiBodyProcessor[TTable, TRow, TKey] {
  this: AbstractRestWebApi[TTable, TRow, TKey] =>

  def bodyToString(request : Request[AnyContent]) : String =
    request.body.asText.getOrElse("")

  def bodyRequestToEntity(request : Request[AnyContent])
  : Option[WebApiEntityResponseWrapper[TRow, TKey]] = {
    val entityWrapper = service.fromJsonToEntityWrapper(request.body.asText)
    val webApiEntityResponseWrapper = WebApiEntityResponseWrapper(
      entityWrapper = entityWrapper,
      bodyToString(request))

    Some(webApiEntityResponseWrapper)
  }

  def bodyRequestToEntities(request          : Request[AnyContent])
  : Option[WebApiEntitiesResponseWrapper[TRow, TKey]] = {
    val entitiesWrapper = service.fromJsonToEntitiesWrapper(request.body.asText)
    val webApiEntitiesResponseWrapper =
      WebApiEntitiesResponseWrapper(entitiesWrapper, bodyToString(request))

    Some(webApiEntitiesResponseWrapper)
  }
}
