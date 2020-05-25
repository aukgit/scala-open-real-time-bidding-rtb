package shared.com.ortb.model.auctionbid.biddingRequests

import shared.com.ortb.demadSidePlatforms.HeightWidthExtractorFromImpression
import shared.com.ortb.model.MinMaxHeightWidthModel
import shared.com.ortb.model.auctionbid.biddingRequests.banners.BannerImpressionModel
import shared.io.helpers.EmptyValidateHelper

/**
 *
 * @param id          : id of the impression
 * @param bidfloor    : Minimum bid for this impression expressed in CPM.
 * @param bidfloorcur : Currency specified using ISO-4217 alpha codes.
 *                    * This may be different from bid currency returned
 *                    * by bidder if this is allowed by the exchange.
 * @param banner      : Banner request information.
 * @param video       : Video request information.
 * @param pmp         : Private market place deals
 */
case class ImpressionModel(
  id : String,

  /**
   * Minimum bid for this impression expressed in CPM.
   */
  bidfloor : Option[Double],

  /**
   * Currency specified using ISO-4217 alpha codes.
   * This may be different from bid currency returned
   * by bidder if this is allowed by the exchange.
   */
  bidfloorcur : Option[String] = Some("USD"),

  /**
   * Banner request information.
   */
  banner : Option[BannerImpressionModel] = None,

  /**
   * Video request information.
   */
  video : Option[VideoImpressionModel] = None,

  /**
   * Private market place deals
   */
  pmp : Option[PrivateMarketPlaceModel] = None
) {
  lazy val hasBanner : Boolean = EmptyValidateHelper.isDefined(banner)
  lazy val hasVideo : Boolean = EmptyValidateHelper.isDefined(video)
  lazy val hasBidFloor : Boolean = EmptyValidateHelper.isDefined(bidfloor)
  lazy val hasBidFloorCurrency : Boolean = EmptyValidateHelper.isOptionStringDefined(bidfloorcur)
  lazy val minMaxHeightWidth : MinMaxHeightWidthModel = HeightWidthExtractorFromImpression.getMinMaxHeightWidths(this)
}
