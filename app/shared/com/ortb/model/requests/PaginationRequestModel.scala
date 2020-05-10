package shared.com.ortb.model.requests

import controllers.webapi.core.AbstractRestWebApi
import play.api.mvc.{ AnyContent, Request }
import shared.com.ortb.model.wrappers.PaginationWrapperModel

case class PaginationRequestModel[TTable, TRow, TKey](
  controller : AbstractRestWebApi[TTable, TRow, TKey],
  request : Request[AnyContent],
  allEntities : Seq[TRow],
  paginationWrapperModel : Option[PaginationWrapperModel]
)


