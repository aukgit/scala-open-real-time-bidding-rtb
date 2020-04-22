package shared.com.ortb.model.repository

import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType

case class ApiPaginationResponseAttributesModel(
  isSuccess : Boolean,
  length : Int,
  domain    : String,
  nextPageSuffix : String,
  previousPageSuffix : String,
  pageSize : Int,
  currentPageIndex : Int,
  totalPagesExist: Int,
  totalCount: Int,
  message : String = "")
