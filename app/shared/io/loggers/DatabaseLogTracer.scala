package shared.io.loggers

import org.joda.time.{ DateTime, DateTimeZone }
import shared.com.ortb.enumeration.LogLevelType
import shared.com.ortb.enumeration.LogLevelType.LogLevelType
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.LogTraceModel
import shared.com.ortb.persistent.repositories.LogTraceRepository
import shared.com.ortb.persistent.schema.Tables._

trait DatabaseLogTracer {
  protected val appManager : AppManager
  protected val className: String
  protected lazy val logTraceRepository : LogTraceRepository = new LogTraceRepository(appManager)

  def getEntityString(item : Option[Any])

  def trace(
    log : LogTraceModel,
    message: String = "LogTrace",
    logLevelType : LogLevelType = LogLevelType.DEBUG) : Unit = {
    val timeInMilliseconds = DateTime.now(DateTimeZone.UTC).getMillis
    new DateTime(timeInMilliseconds)
    val requestString = log.request



    val row = LogtraceRow(
      -1,
      Some(log.methodName),
      Some(className),
      Some(requestString),
      Some(log.message),
      Some(log.entityData),
      log.databaseTransactionId,
      Some(timeInMilliseconds.asInstanceOf[Double]))

    AppLogger.logAsJson(
      message,
      logLevelType = logLevelType,
      nullableObject = Some(row))

    logTraceRepository.add(row)
  }
}
