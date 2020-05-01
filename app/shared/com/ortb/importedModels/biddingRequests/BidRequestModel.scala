package shared.com.ortb.importedModels.biddingRequests

/**
 *
 * @param id     : Bidding Id
 * @param imp    : Gives details of requesting advertise type Banner, Video or Native one.
 * @param site   : Site is used for targeting sites
 * @param user   : Site is used for targeting sites
 * @param device : Device based on advertise details.
 */
case class BidRequestModel(
  id : String,

  /**
   * Gives details of requesting advertise type Banner, Video or Native one.
   */
  imp : Option[List[ImpressionModel]],

  /**
   * Site is used for targeting sites
   */
  site : Option[SiteModel],

  /**
   * User is used for targeting user detail, like by age, gender and so on.
   */
  user : Option[UserModel],

  /**
   * Device based on advertise details.
   */
  device : Option[DeviceModel])

