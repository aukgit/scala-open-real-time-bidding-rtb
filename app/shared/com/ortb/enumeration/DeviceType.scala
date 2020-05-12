package shared.com.ortb.enumeration

/**
 * 1 - MobileTablet
 * 2 - PersonalComputer
 * 3 - ConnectedTv
 * 4 - Phone
 * 5 - Tablet
 * 5 - ConnectedDevice
 * 6 - SetTopBox
 */
object DeviceType extends Enumeration {
  type ContentContextType = Value
  val
  MobileTablet,
  PersonalComputer,
  ConnectedTv,
  Phone,
  Tablet,
  ConnectedDevice,
  SetTopBox = Value
}
