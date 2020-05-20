package shared.com.ortb.enumeration

import enumeratum.values._

sealed abstract class DatabaseActionType(val value : String) extends StringEnumEntry

case object DatabaseActionType extends StringEnum[DatabaseActionType] with StringCirceEnum[DatabaseActionType] {

  case object Create extends DatabaseActionType("Create")

  case object Read extends DatabaseActionType("Read")

  case object Update extends DatabaseActionType("Update")

  case object Delete extends DatabaseActionType("Delete")

  case object AddOrUpdate extends DatabaseActionType("AddOrUpdate")

  case object Unknown extends DatabaseActionType("Unknown")

  case object Combined extends DatabaseActionType("Combined")

  lazy val values : IndexedSeq[DatabaseActionType] = findValues
}
