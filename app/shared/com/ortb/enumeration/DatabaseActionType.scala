package shared.com.ortb.enumeration

import enumeratum.values.{ IntCirceEnum, IntEnum, IntEnumEntry, StringCirceEnum, StringEnum, StringEnumEntry }
import shared.com.ortb.executors.CirceLibraryItem
import shared.com.ortb.executors.CirceLibraryItem.findValues

object DatabaseActionType extends Enumeration {
  type DatabaseActionType = Value
  val Create, Read, Update, Delete, AddOrUpdate, Unknown, Combined = Value
}

sealed abstract class DatabaseActionEnum(val value: String) extends StringEnumEntry

case object DatabaseActionType extends StringEnum[DatabaseActionEnum] with StringCirceEnum[DatabaseActionEnum] {

  // A good mix of named, unnamed, named + unordered args
  case object Create     extends DatabaseActionEnum(value = nameOf())
  case object Read    extends DatabaseActionEnum(name = "movie", value = 2)
  case object Update extends DatabaseActionEnum(3, "magazine")
  case object Delete       extends DatabaseActionEnum(4, name = "cd")

  val values = findValues
}
