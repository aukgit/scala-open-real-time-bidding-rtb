package shared.com.ortb.webapi.traits

import play.api.mvc.{AnyContent, Request}
import shared.com.ortb.model.wrappers.persistent.{WebApiEntitiesResponseWrapper, WebApiEntityResponseWrapper}

trait RestWebApiBodyProcessor[TTable, TRow, TKey] {

  def toString(request : Request[AnyContent]) : String

  def bodyRequestToEntity(request : Request[AnyContent]) : Option[WebApiEntityResponseWrapper[TRow, TKey]]

  def bodyRequestToEntities(request : Request[AnyContent]) : Option[WebApiEntitiesResponseWrapper[TRow, TKey]]

  def toJson[T](item : Option[T]) : String

  def toJson[T](items : Option[Iterable[T]]) : String
}
