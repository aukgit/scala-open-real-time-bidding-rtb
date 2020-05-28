package shared.com.ortb.persistent.schema

import shared.com.ortb.persistent.schema.Tables._
import slick.lifted.TableQuery

class DatabaseSchemaViews {
  lazy val advertiseIsRunningViewName = "AdvertiseIsRunningView"
  lazy val bidRelatedIdsViewName = "BidRelatedIdsView"
  lazy val keywordAdvertiseMappingIdsViewName = "KeywordAdvertiseMappingIdsView"
  lazy val bidRequestImpressionWithPlaceholderViewName = "BidRequestImpressionWithPlaceholderView"
  lazy val winningPriceInfoViewName = "WinningPriceInfoView"

  lazy val advertiseIsRunningView = TableQuery[Advertiseisrunningview]
  lazy val bidRelatedIdsView = TableQuery[Bidrelatedidsview]
  lazy val bidRequestImpressionWithPlaceholderView = TableQuery[Bidrequestimpressionwithplaceholderview]
  lazy val keywordAdvertiseMappingIdsView = TableQuery[Keywordadvertisemappingidsview]
  lazy val winningPriceInfoView = TableQuery[Winningpriceinfoview]

  lazy val views = Map(
    advertiseIsRunningViewName -> advertiseIsRunningView,
    bidRelatedIdsViewName -> bidRelatedIdsView,
    bidRequestImpressionWithPlaceholderViewName -> bidRequestImpressionWithPlaceholderView,
    keywordAdvertiseMappingIdsViewName -> keywordAdvertiseMappingIdsView,
    winningPriceInfoViewName -> winningPriceInfoView
  )

  lazy val viewNames = List(
    advertiseIsRunningViewName,
    bidRelatedIdsViewName,
    bidRequestImpressionWithPlaceholderViewName,
    keywordAdvertiseMappingIdsViewName,
    winningPriceInfoViewName
  )
}
