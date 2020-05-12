package shared.com.ortb.enumeration

object ControllerDefaultActionType extends Enumeration {
  type ControllerDefaultActionType = Value
  val
  Routing,
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
