package shared.com.ortb.webapi.traits

import controllers.webapi.core.RestWebApiContracts
import play.api.mvc.{ AnyContent, Request }
import shared.com.ortb.model.repository.response.RepositoryOperationResultsModel
import shared.com.ortb.model.wrappers.persistent.EntityWrapperWithOptions

trait RestWebApiEntityJsonAdapterImplementation[TTable, TRow, TKey]
{
  this : RestWebApiContracts[TTable, TRow, TKey] =>

  protected def fromJsonToEntity(jsonString : Option[String]) :
  Option[EntityWrapperWithOptions[TRow, TKey]] =>
    service.type.fromJsonToEntity(jsonString)

  def fromRequestToEntity(request : Option[Request[AnyContent]]) :
  Option[EntityWrapperWithOptions[TRow, TKey]]

  def fromRequestToEntities(request : Option[Request[AnyContent]]) :
  Option[RepositoryOperationResultsModel[TRow, TKey]]

  def fromJsonToEntities(jsonString : Option[String]) :
  Option[RepositoryOperationResultsModel[TRow, TKey]]
}
