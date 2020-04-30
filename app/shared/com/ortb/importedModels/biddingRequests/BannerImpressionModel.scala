package shared.com.ortb.importedModels.biddingRequests

case class BannerImpressionModel(
  id: Option[String],
  wmin : Option[Int],
  wmax : Option[Int],
  w : Option[Int],
  hmin : Option[Int],
  hmax : Option[Int],
  h : Option[Int],

  /**
   * Ad position on screen.
   */
  pos : Option[Int],

  /**
   * Content MIME types supported.
   * Popular MIME types may include “application/x-shockwave-flash”,
   */
  mimes: Option[List[String]],

  /**
   * Indicates if the banner is in the top frame as opposed to an iframe, where 0 = no, 1 = yes.
   */
  topframe: Option[Int])
