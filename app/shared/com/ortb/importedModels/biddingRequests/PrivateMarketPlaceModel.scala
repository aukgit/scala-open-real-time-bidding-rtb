package shared.com.ortb.importedModels.biddingRequests

/**
 *
 * @param private_auction : Indicator of auction eligibility to seats named
 *                          in the Direct Deals object, where 0 = all bids are accepted,
 *                          1 = bids are restricted to the deals specified and the terms thereof.
 * @param deals : Array of Deal (Section 3.2.18) objects
 *                that convey the specific deals applicable to this impression.
 */
case class PrivateMarketPlaceModel(
  private_auction : String,
  deals: PrivateMarketPlaceDealModel)
