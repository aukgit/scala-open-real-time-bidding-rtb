package shared.com.ortb.importedModels.biddingRequests

/**
 * Banner Details inside the BidRequest -> Impression[] -> Impression -> banner
 *
 * @param id       : Banner id
 * @param wmin     : If included along with a w value then
 *                 * w should be interpreted as a
 *                 * recommended or preferred width.
 * @param wmax     : Maximum width of the impression in pixels.
 *                 * If included along with a w value then w should be
 *                 * interpreted as a recommended or preferred width.
 * @param w        : If neither wmin nor wmax are specified,
 *                 * this value is an exact width requirement.
 *                 * Otherwise it is a preferred width.
 * @param hmin     : If included along with an h value then h
 *                 * should be interpreted as a
 *                 * recommended or preferred height.
 * @param hmax     : If included along with an h value then h should be
 *                 * interpreted as a recommended or preferred height.
 * @param h        : If neither hmin nor hmax are specified,
 *                 * this value is an exact height requirement.
 *                 * Otherwise it is a preferred height.
 * @param pos      : Ad position on screen.
 * @param mimes    : Content MIME types supported.
 *                 * Popular MIME types may include
 *                 * “application/x-shockwave-flash”,
 * @param topframe : Indicates if the banner is in the
 *                 * top frame as opposed to an iframe,
 *                 * where 0 = no, 1 = yes.
 */
case class BannerImpressionModel(
  id : Option[String],

  /**
   * If included along with a w value then
   * w should be interpreted as a
   * recommended or preferred width.
   */
  wmin : Option[Int],

  /**
   * Maximum width of the impression in pixels.
   * If included along with a w value then w should be
   * interpreted as a recommended or preferred width.
   */
  wmax : Option[Int],

  /**
   * If neither wmin nor wmax are specified,
   * this value is an exact width requirement.
   * Otherwise it is a preferred width.
   */
  w : Option[Int],

  /**
   * If included along with an h value then h
   * should be interpreted as a
   * recommended or preferred height.
   */
  hmin : Option[Int],

  /**
   * If included along with an h value then h should be
   * interpreted as a recommended or preferred height.
   */
  hmax : Option[Int],

  /**
   * If neither hmin nor hmax are specified,
   * this value is an exact height requirement.
   * Otherwise it is a preferred height.
   */
  h : Option[Int],

  /**
   * Ad position on screen.
   */
  pos : Option[Int],

  /**
   * Content MIME types supported.
   * Popular MIME types may include
   * “application/x-shockwave-flash”,
   */
  mimes : Option[List[String]],

  /**
   * Indicates if the banner is in the
   * top frame as opposed to an iframe,
   * where 0 = no, 1 = yes.
   */
  topframe : Option[Int],

  /**
   * Blocked banner ad types. Refer to List 5.2.
   */
  btype : Option[List[Int]],

  /**
   * Blocked creative attributes. Refer to List 5.3.
   */
  battr : Option[List[Int]])
