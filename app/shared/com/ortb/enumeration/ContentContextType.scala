package shared.com.ortb.enumeration

import enumeratum.values.{ IntCirceEnum, IntEnum, IntEnumEntry }

/**
 * 1 - Video
 * 2 - Game
 * 3 - Music
 * 4 - Application
 * 5 - TextOrWebOrEbook
 * 5 - OtherUnknown
 * 6 - Unknown
 */
sealed abstract class ContentContextType(val value : Int) extends IntEnumEntry

/**
 * 1 - Video
 * 2 - Game
 * 3 - Music
 * 4 - Application
 * 5 - TextOrWebOrEbook
 * 5 - OtherUnknown
 * 6 - Unknown
 */
case object ContentContextType extends IntEnum[ContentContextType] with IntCirceEnum[ContentContextType] {

  lazy val values : IndexedSeq[ContentContextType] = findValues

  case object Video extends ContentContextType(1)

  case object Game extends ContentContextType(2)

  case object Music extends ContentContextType(3)

  case object Application extends ContentContextType(4)

  case object Text extends ContentContextType(5)

  case object Other extends ContentContextType(6)

  case object Unknown extends ContentContextType(7)

}
