package shared.io.helpers.traits

import play.api.mvc.{ AnyContent, Request }
import shared.com.ortb.model.requests.PaginationRequestModel
import shared.com.ortb.model.wrappers.PaginationWrapperModel

trait PaginationHelperBase {
  def getPaginationWrapperModel(request : Request[AnyContent]) : Option[PaginationWrapperModel]

  /**
   * Based on the request given serves JSON string for serving.
   * @param paginationRequestModel
   * @tparam TTable
   * @tparam TRow
   * @tparam TKey
   * @return
   */
  def getPaginationResponse[TTable, TRow, TKey](
    paginationRequestModel : PaginationRequestModel[TTable, TRow, TKey]
  ) : String
}
