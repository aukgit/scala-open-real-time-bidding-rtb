package shared.io.helpers

import play.api.mvc.{ AnyContent, Request }
import shared.com.ortb.constants.AppConstants
import shared.com.ortb.model.attributes.ApiPaginationResponseAttributesModel
import shared.com.ortb.model.requests.PaginationRequestModel
import shared.com.ortb.model.results.ApiPaginationResultsModel
import shared.com.ortb.model.wrappers.PaginationWrapperModel
import shared.io.helpers.traits.PaginationHelperBase
import shared.io.loggers.AppLogger

object PaginationHelper extends PaginationHelperBase {
  def getPaginationWrapperModel(request : Request[AnyContent]) : Option[PaginationWrapperModel] = {
    try {
      val page = request.getQueryString(AppConstants.QueryStringNameConstants.page)
      if (EmptyValidateHelper.isEmpty(page)) {
        return None
      }

      val currentPageSize = request.getQueryString(AppConstants.QueryStringNameConstants.pageSize)
      val pageToInt = page.get.toInt

      if (EmptyValidateHelper.isDefined(currentPageSize)) {
        return Some(PaginationWrapperModel(pageToInt, currentPageSize.get.toInt))
      }

      return Some(PaginationWrapperModel(pageToInt))
    } catch {
      case e : Exception => AppLogger.error(e)
    }

    None
  }

  def getPaginationResponse[TTable, TRow, TKey](
    paginationRequestModel : PaginationRequestModel[TTable, TRow, TKey]
  ) : String = {
    val allEntities = paginationRequestModel.allEntities
    val length = allEntities.length
    val paginationWrapperModel = paginationRequestModel.paginationWrapperModel
    val paged = paginationWrapperModel.get
    val service = paginationRequestModel.controller.service
    val count = service.repository.count.get
    val totalPages = Math.ceil(count / paged.pageSize.asInstanceOf[Double]).asInstanceOf[Int]
    // TODO get the next page and prev page using the page logic.

    val apiAttribute = ApiPaginationResponseAttributesModel(
      length > 0,
      length,
      "domain",
      "nextPage",
      "prevPage",
      paged.pageSize,
      paged.page,
      totalPages,
      count)

    val apiModel = ApiPaginationResultsModel[TRow, TKey](Some(apiAttribute), allEntities)
    val encoder = service.serviceEncoders.apiPaginationEncoder
    val finalJson = encoder.genericJsonParser
      .toJsonString(Some(apiModel))

    finalJson.get
  }
}