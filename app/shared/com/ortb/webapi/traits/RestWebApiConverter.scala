package shared.com.ortb.webapi.traits

import play.api.mvc.{AnyContent, Request}
import shared.com.ortb.model.wrappers.persistent.EntityWrapperWithOptions

trait RestWebApiConverter[TTable, TRow, TKey] {

  def fromJsonToEntity(jsonString: Option[String]) :
  Option[EntityWrapperWithOptions[TRow, TKey]]

  def fromRequestToEntity(request: Option[Request[AnyContent]]) :
  Option[EntityWrapperWithOptions[TRow, TKey]]

  def fromRequestToEntities(request: Option[Request[AnyContent]]) :
  Option[Iterable[EntityWrapperWithOptions[TRow, TKey]]]

  def fromJsonToEntities(jsonString: Option[String]) :
  Option[Iterable[EntityWrapperWithOptions[TRow, TKey]]]
}
