package shared.com.ortb.importedModels.biddingRequests

/**
 * Short for PMP
 * @param id
 * @param banner
 * @param bidFloor
 */
case class VideoImpressionModel(
  /**
   * Required:
   * Content MIME types supported.
   * Popular MIME types may include
   * “video/x-ms-wmv” for Windows Media
   * and “video/x-flv” for Flash Video.
   */
  mimes : List[String],
  minduration: Option[Int],
  maxduration: Option[Int],
  protocols: Option[Int],
  w: Option[Int],
  h: Option[Int],

  /**
   * Indicates the start delay in
   * seconds for pre-roll, mid-roll,
   * or post-roll ad placements.
   * Refer to List 5.10 for additional generic values.
   */
  startdelay: Option[Int],
  linearity: Option[Int],
  bidFloor : Option[Double])
