package shared.com.ortb.model.attributes

case class GenericControllerResponseAttributesModel[TKey](
  isSuccess : Boolean,
  databaseActionPerformed: String = "",
  entityIds: Iterable[TKey],
  message : String = "")
