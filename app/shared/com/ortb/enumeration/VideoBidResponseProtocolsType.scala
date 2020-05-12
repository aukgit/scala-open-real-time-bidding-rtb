package shared.com.ortb.enumeration

/**
 * 1 - VAST 1.0
 * 2 - VAST 2.0 and so on
 */
object VideoBidResponseProtocolsType extends Enumeration {
  type VideoBidResponseProtocolsType = Value
  val
  VAST1,
  VAST2,
  VAST3,
  VAST1Wrapper,
  VAST2Wrapper,
  VAST3Wrapper = Value
}
