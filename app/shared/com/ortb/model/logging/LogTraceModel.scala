package shared.com.ortb.model.logging

case class LogTraceModel(
  methodName : String,
  request : Option[Any] = None,
  message : String = "",
  entity : Option[Any] = None,
  entities : Option[Iterable[Any]] = None,
  databaseTransactionType : Option[String] = None
)
