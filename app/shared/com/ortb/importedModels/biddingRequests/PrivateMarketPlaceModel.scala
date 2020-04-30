package shared.com.ortb.importedModels.biddingRequests

/**
 *
 * @param private_auction
 * @param deals
 */
case class PrivateMarketPlaceModel(
  private_auction : String,
  deals: PrivateMarketPlaceDealModel)
