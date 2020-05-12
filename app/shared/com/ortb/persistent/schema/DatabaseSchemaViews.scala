package shared.com.ortb.persistent.schema

import slick.lifted.TableQuery
import shared.com.ortb.persistent.schema.Tables._

class DatabaseSchemaViews {
  lazy val bidRelatedIdsViewName = "BidRelatedIdsView"
  lazy val keywordAdvertiseMappingIdsViewName = "KeywordAdvertiseMappingIdsView"
  lazy val winningPriceInfoViewName = "WinningPriceInfoView"

  lazy val bidRelatedIdsView = TableQuery[Bidrelatedidsview]
  lazy val keywordAdvertiseMappingIdsView = TableQuery[Keywordadvertisemappingidsview]
  lazy val winningPriceInfoView = TableQuery[Winningpriceinfoview]

  lazy val views = Map(
    bidRelatedIdsViewName -> bidRelatedIdsView,
    keywordAdvertiseMappingIdsViewName -> keywordAdvertiseMappingIdsView,
    winningPriceInfoViewName -> winningPriceInfoView
  )

  lazy val viewNames = List(
    bidRelatedIdsViewName,
    keywordAdvertiseMappingIdsViewName,
    winningPriceInfoViewName
  )
}
