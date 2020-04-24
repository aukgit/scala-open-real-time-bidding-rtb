package shared.com.ortb.enumeration

object ControllerDefaultActionType extends Enumeration {
  type ControllerDefaultActionType = Value
  val
  GetOrRead,
  Add,
  Update,
  Delete,
  PartialUpdate,
  AddMany,
  UpdateMany,
  DeleteMany,
  AddOrUpdate,
  AddOrUpdateMany = Value
}
