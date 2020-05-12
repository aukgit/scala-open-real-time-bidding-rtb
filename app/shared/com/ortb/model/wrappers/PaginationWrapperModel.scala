package shared.com.ortb.model.wrappers

import shared.com.ortb.constants.AppConstants

case class PaginationWrapperModel(page : Int, pageSize : Int = AppConstants.DefaultPageSize)
