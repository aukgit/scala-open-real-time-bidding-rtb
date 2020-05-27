package shared.com.ortb.model.auctionbid.biddingRequests

import shared.com.ortb.model.dimensions.HeightWidthBaseModel

/**
 * Video Request(for Bid) Details inside the BidRequest -> Impression[] -> Impression -> video
 *
 * @param mimes         : Required:
 *                      * Content MIME types supported.
 *                      * Popular MIME types may include
 *                      * “video/x-ms-wmv” for Windows Media
 *                      * and “video/x-flv” for Flash Video.
 * @param minduration   : Minimum video ad duration in seconds.
 * @param maxduration   : Maximum video ad duration in seconds.
 * @param protocols     : Array of supported video bid response protocols.
 *                      * Refer to List 5.8.
 *                      * At least one supported protocol
 *                      * must be specified in either the protocol or protocols attribute
 * @param w             : Width of the video player in pixels.
 * @param h             : Height of the video player in pixels.
 * @param startdelay    : Indicates the start delay in
 *                      * seconds for pre-roll, mid-roll,
 *                      * or post-roll ad placements.
 *                      * Refer to List 5.10 for additional generic values.
 * @param linearity     : Indicates if the impression must be linear,
 *                      nonlinear, etc. If none specified,
 *                      assume all are allowed. Refer to List 5.7.
 * @param boxingallowed : default 1
 *                      * Indicates if letter-boxing of 4:3 content into a
 *                      * 16:9 window is allowed, where 0 = no, 1 = yes.
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

  /**
   * Minimum video ad duration in seconds.
   */
  minduration : Option[Int] = None,

  /**
   * Maximum video ad duration in seconds.
   */
  maxduration : Option[Int] = None,

  /**
   * Array of supported video bid response protocols.
   * Refer to List 5.8.
   * At least one supported protocol
   * must be specified in either the protocol or protocols attribute.
   */
  protocols : Option[Int] = None,

  /**
   * Width of the video player in pixels.
   */
  w : Option[Int] = None,

  /**
   * Height of the video player in pixels.
   */
  h : Option[Int] = None,

  /**
   * Indicates the start delay in
   * seconds for pre-roll, mid-roll,
   * or post-roll ad placements.
   * Refer to List 5.10 for additional generic values.
   */
  startdelay : Option[Int] = None,

  /**
   * Indicates if the impression must be linear, nonlinear, etc. If none specified, assume all are allowed. Refer to List 5.7.
   */
  linearity : Option[Int] = None,

  /**
   * default 1
   * Indicates if letter-boxing of 4:3 content into a
   * 16:9 window is allowed, where 0 = no, 1 = yes.
   */
  boxingallowed : Option[Int] = Some(1))
  extends HeightWidthBaseModel(h, w)
