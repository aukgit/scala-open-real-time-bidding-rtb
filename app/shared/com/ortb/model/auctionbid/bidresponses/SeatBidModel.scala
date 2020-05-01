package shared.com.ortb.model.auctionbid.bidresponses

import shared.com.ortb.constants.AppConstants

/**
 * BidResponse -> seatbid[] -> Single SeatBid object.
 *
 * @param bid : Array of 1+ Bid objects (Section 4.2.3) each related to an impression.
 *            * Multiple bids can relate to the same impression.
 * @param seat  : ID of the bidder seat on whose behalf this bid is made.
 * @param group : 0 = impressions can be won individually;
 *              * 1 = impressions must be won or lost as a group.
 *              * 1 = Meaning all the items in the seatbid needs to won as a group (My guess, no R&D yet).
 */
case class SeatBidModel (
  /**
   * Array of 1+ Bid objects (Section 4.2.3) each related to an impression.
   * Multiple bids can relate to the same impression.
   */
  bid: List[BidModel],

  /**
   * ID of the bidder seat on whose behalf this bid is made.
   */
  seat : Option[String],

  /**
   * 0 = impressions can be won individually;
   * 1 = impressions must be won or lost as a group.
   * 1 = Meaning all the items in the seatbid needs to won as a group (My guess, no R&D yet).
   */
  group : Option[Int] = AppConstants.EmptyIntegerOption
)
