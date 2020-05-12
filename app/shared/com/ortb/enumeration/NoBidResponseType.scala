package shared.com.ortb.enumeration

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
object NoBidResponseType extends Enumeration {
  type NoBidResponseType = Value
  val
  UnknownError,
  TechnicalError,
  InvalidRequest,
  KnownWebSpider,
  SuspectedNonHumanTraffic,
  CloudDataCenterOrProxy,
  UnsupportedDevice,
  BlockedPublisherOrSite,
  UnmatchedUser = Value
}