package shared.com.ortb.model

case class LogTraceModel(
  methodName : Option[String],
  className : Option[String],
  request : Option[String],
  message : Option[String] = None,
  entityData : Option[String],
  databaseTransactionId : Option[Int] = None
)
