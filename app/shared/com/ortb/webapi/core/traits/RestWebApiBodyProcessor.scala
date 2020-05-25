package shared.com.ortb.webapi.core.traits

import play.api.libs.json.Writes
import play.api.mvc.{ Action, _ }
import play.libs.Json
import play.mvc.Http.MimeTypes
import shared.com.ortb.model.wrappers.persistent.{ WebApiEntitiesResponseWrapper, WebApiEntityResponseWrapper }

trait RestWebApiBodyProcessor[TTable, TRow, TKey] {

  def bodyToString(request : Request[AnyContent]) : String

  def bodyRequestToEntity(request : Request[AnyContent]) :
  Option[WebApiEntityResponseWrapper[TRow, TKey]]

  def bodyRequestToEntities(request : Request[AnyContent]) : Option[WebApiEntitiesResponseWrapper[TRow, TKey]]
}
