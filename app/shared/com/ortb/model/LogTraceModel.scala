package shared.com.ortb.model

case class LogTraceModel(
  methodName : String,
  request : String = "",
  message : String = "",
  entityData : Option[Any] = None,
  entitiesData : Option[Iterable[Any]] = None,
  databaseTransactionId : Option[Int] = None
)
