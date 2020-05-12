package shared.com.ortb.enumeration

/**
 * 1 - Video
 * 2 - Game
 * 3 - Music
 * 4 - Application
 * 5 - TextOrWebOrEbook
 * 5 - OtherUnknown
 * 6 - Unknown
 */
object ContentContextType extends Enumeration {
  type ContentContextType = Value
  val
  Video,
  Game,
  Music,
  Application,
  TextOrWebOrEbook,
  OtherUnknown,
  Unknown = Value
}
