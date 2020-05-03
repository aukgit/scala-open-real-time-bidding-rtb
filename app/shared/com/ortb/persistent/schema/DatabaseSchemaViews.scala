package shared.com.ortb.persistent.schema

import slick.lifted.TableQuery
import shared.com.ortb.persistent.schema.Tables._

class DatabaseSchemaViews {
  lazy val bidRelatedIdsViewName = "BidRelatedIdsView"
  lazy val keywordAdvertiseMappingIdsViewName = "KeywordAdvertiseMappingIdsView"
  lazy val winningPriceInfoViewName = "WinningPriceInfoView"

  lazy val BidRelatedIdsView = TableQuery[Bidrelatedidsview]
  lazy val KeywordAdvertiseMappingIdsView = TableQuery[Keywordadvertisemappingidsview]
  lazy val winningPriceInfoView = TableQuery[Winningpriceinfoview]

  lazy val views = Map(
    bidRelatedIdsViewName -> BidRelatedIdsView,
    keywordAdvertiseMappingIdsViewName -> KeywordAdvertiseMappingIdsView,
    winningPriceInfoViewName -> winningPriceInfoView
  )

  lazy val viewNames = List(
    bidRelatedIdsViewName,
    keywordAdvertiseMappingIdsViewName,
    winningPriceInfoViewName
  )
}
