package shared.com.ortb.model.attributes

case class GenericControllerResponseAttributesModel(
  isSuccess : Boolean,
  databaseActionPerformed : String = "",
  requestUri : String = "",
  entityIds : Iterable[String],
  message            : String = "")
