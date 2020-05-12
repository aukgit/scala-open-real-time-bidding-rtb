package shared.com.ortb.model.auctionbid.biddingRequests

case class UserModel(
  id : Option[String],
  buyerid : Option[String],

  /**
   * Year of birth as a 4-digit integer
   */
  yob : Option[Int],
  gender : Option[Char],

  /**
   * Comma separated list of keywords, interests, or intent.
   */
  keywords : Option[List[String]],

  /**
   * Optional feature to pass bidder data that was set in the exchange’s cookie.
   * The string must be in base85 cookie safe characters and be in any format.
   * Proper JSON encoding must be used to include “escaped” quotation marks.
   */
  customdata : Option[String],
  geo : Option[GeoModel])