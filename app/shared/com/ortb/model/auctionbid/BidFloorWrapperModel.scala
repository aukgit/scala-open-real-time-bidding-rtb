package shared.com.ortb.model.auctionbid

import shared.com.ortb.constants.AppConstants

case class BidFloorWrapperModel(
  bidFloor : Double = 0,
  bidFloorCurrency : Option[String] = Some(AppConstants.CurrencyUsd)
)
