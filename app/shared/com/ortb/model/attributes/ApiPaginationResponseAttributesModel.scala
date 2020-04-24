package shared.com.ortb.model.attributes

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


