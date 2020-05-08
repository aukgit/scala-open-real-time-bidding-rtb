package shared.com.ortb.enumeration

import enumeratum.values._

case object ReflectionModifier extends IntEnum[ReflectionModifier] with IntCirceEnum[ReflectionModifier] {

  case object Private extends ReflectionModifier(2)

  case object Public extends ReflectionModifier(1)

  case object Static extends ReflectionModifier(8)

  case object Synchronized extends ReflectionModifier(32)

  case object Interface extends ReflectionModifier(512)

  case object Final extends ReflectionModifier(16)

  case object Abstract extends ReflectionModifier(1024)

  case object Protected extends ReflectionModifier(4)

  lazy val values : IndexedSeq[ReflectionModifier] = findValues
}

sealed abstract class ReflectionModifier(val value : Int) extends IntEnumEntry