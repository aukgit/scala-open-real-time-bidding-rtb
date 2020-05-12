package shared.com.ortb.model.auctionbid.biddingRequests

/**
 *
 * @param id       : System id for the content
 * @param series   : Video Examples: “The Office” (television), “Star Wars” (movie), or “Arby ‘N’ The Chief” (made for web).
 * @param season   : Content season; typically for video content (e.g., “Season 3”).
 * @param title    : Example “Why an Antarctic Glacier Is Melting So Quickly”
 * @param cat      : IAB15, IAB15-1, IAB17 (sports)
 * @param keywords : CSV string for keywords
 */
case class ContentSiteModel(
  id : Option[String],

  /**
   * Content title.
   * Video Examples: “Search Committee” (television), “A New Hope” (movie), or “Endgame” (made for web).
   * Non-Video Example: “Why an Antarctic Glacier Is Melting So Quickly” (Time magazine article).
   */
  title : Option[String],

  /**
   * Content series.
   * Video Examples: “The Office” (television), “Star Wars” (movie), or “Arby ‘N’ The Chief” (made for web).
   * Non-Video Example: “Ecocentric” (Time Magazine blog).
   */
  series : Option[String],

  /**
   * Content season; typically for video content (e.g., “Season 3”).
   */
  season : Option[String],

  /**
   * Category : IAB15, IAB15-1, IAB17 (sports)
   */
  cat : Option[List[String]],

  /**
   * CSV string for keywords
   * keyword-a,keyword-b,keyword-c
   */
  keywords : Option[String],

  url : Option[String],

  /**
   * Content language using ISO-639-1-alpha-2.
   */
  language : Option[String] = None
)
