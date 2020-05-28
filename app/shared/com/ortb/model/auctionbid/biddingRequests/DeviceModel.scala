package shared.com.ortb.model.auctionbid.biddingRequests

case class DeviceModel(
  /**
   * Recommended
   * UserAgent/Browser Agent
   * Browser user agent string.
   */
  ua : Option[String] = None,

  /**
   * Recommended
   * Location of the device assumed to be the
   * user’s current location defined
   * by a Geo object (Section 3.2.12).
   */
  geo : Option[GeoModel] = None,

  /**
   * Recommended
   * Standard “Do Not Track” flag as set in the header by the browser,
   * where 0 = tracking is unrestricted, 1 = do not track.
   */
  dnt : Option[Int] = None,

  /**
   * Recommended
   * “Limit Ad Tracking” signal commercially
   * endorsed (e.g., iOS, Android),
   * where 0 = tracking is unrestricted,
   * 1 = tracking must be limited per commercial guidelines.
   */
  lmt : Option[Int] = None,

  /**
   * Recommended
   * IPv4 address closest to device.
   */
  ip : Option[String] = None,

  /**
   * IP address closest to device as IPv6.
   */
  ipv6 : Option[String] = None,

  /**
   * The general type of device. Refer to List 5.17.
   * 1 - Mobile/Tablet - Version 2.0
   * 2 - Personal Computer - Version 2.0
   * 3 - Connected TV - Version 2.0
   * 4 - Phone - New for Version 2.2
   * 5 - Tablet - New for Version 2.2
   * 6 - Connected Device - New for Version 2.2
   * 7 - Set Top Box - New for Version 2.2
   */
  devicetype : Option[Int] = None,

  /**
   * Device creator
   * (e.g., “Apple”).
   */
  make : Option[String] = None,

  /**
   * Device model (e.g., “iPhone”).
   */
  model : Option[String] = None,

  /**
   * Device operating system (e.g., “iOS”).
   */
  os : Option[String] = None,

  /**
   * Physical height of the screen in pixels.
   */
  h : Option[Int] = None,

  /**
   * Physical width of the screen in pixels.
   */
  w : Option[Int] = None,

  /**
   * Screen size as pixels per linear inch.
   */
  ppi : Option[Int] = None,

  /**
   * Support for JavaScript, where 0 = no, 1 = yes.
   */
  js : Option[Int] = None,

  /**
   * Version of Flash supported by the browser.
   */
  flashver : Option[String] = None
)
