package shared.com.ortb.enumeration

import enumeratum.values.{ IntCirceEnum, IntEnum, IntEnumEntry }

/**
 * 0 - Unknown Error
 * 1 - Technical Error
 * 2 - Invalid Request
 * 3 - Known Web Spider
 * 4 - Suspected Non-Human Traffic
 * 5 - Cloud, Data center, or Proxy IP
 * 6 - Unsupported Device
 * 7 - Blocked Publisher or Site
 * 8 - Unmatched User
 */
sealed abstract class NoBidResponseType(val value : Int) extends IntEnumEntry

/**
 * 0 - Unknown Error
 * 1 - Technical Error
 * 2 - Invalid Request
 * 3 - Known Web Spider
 * 4 - Suspected Non-Human Traffic
 * 5 - Cloud, Data center, or Proxy IP
 * 6 - Unsupported Device
 * 7 - Blocked Publisher or Site
 * 8 - Unmatched User
 */
case object NoBidResponseType extends IntEnum[NoBidResponseType] with IntCirceEnum[NoBidResponseType] {

  lazy val values : IndexedSeq[NoBidResponseType] = findValues

  case object UnknownError extends NoBidResponseType(0)

  case object TechnicalError extends NoBidResponseType(1)

  case object InvalidRequest extends NoBidResponseType(2)

  case object KnownSpider extends NoBidResponseType(3)

  case object SuspectedNonHumanTraffic extends NoBidResponseType(4)

  case object CloudDataCenterOrProxyIp extends NoBidResponseType(5)

  case object UnsupportedDevice extends NoBidResponseType(6)

  case object BlockedPublisherOrSite extends NoBidResponseType(7)

  case object UnmatchedUser extends NoBidResponseType(8)
}