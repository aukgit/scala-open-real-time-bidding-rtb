package shared.io.loggers

import shared.com.ortb.persistent.schema.Tables._
import org.joda.time.{ DateTime, DateTimeZone }
import shared.com.ortb.constants.AppConstants
import shared.com.ortb.enumeration.LogLevelType
import shared.com.ortb.enumeration.LogLevelType.LogLevelType
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.logging.LogTraceModel
import shared.com.ortb.persistent.repositories.LogTraceRepository
import shared.io.helpers.{ EmptyValidateHelper, ToStringHelper }

trait DatabaseLogTracer {
  protected val appManager : AppManager
  protected val className : String
  protected lazy val logTraceRepository : LogTraceRepository = new LogTraceRepository(appManager)

  def trace(
    log : LogTraceModel,
    message : String = "LogTrace",
    logLevelType : LogLevelType = LogLevelType.DEBUG) : Unit = {
    val timeInMilliseconds = DateTime.now(DateTimeZone.UTC).getMillis
    val toDateSample = new DateTime(timeInMilliseconds)

    AppLogger.debug("Date", toDateSample.toString)

    var entityString : Option[String] =AppConstants.EmptyStringOption
    var requestString : Option[String] = AppConstants.EmptyStringOption

    try{
      if (EmptyValidateHelper.isDefined(log.entity)) {
        entityString = ToStringHelper.toStringOf(log.entity)
      }

      if (EmptyValidateHelper.hasAnyItem(log.entities)) {
        val prevString = entityString.get
        val entitiesString = ToStringHelper.toStringOfItems(log.entities).get
        entityString = Some(prevString  + "\n"+ entitiesString)
      }
    }
    catch {
      case e:Exception => AppLogger.error(e)
    }

    try{
      if(EmptyValidateHelper.isDefined(log.request)){
        requestString = Some(log.request.get.toString)
      }
    }
    catch {
      case e:Exception => AppLogger.error(e)
    }

    val row = LogtraceRow(
      -1,
      Some(log.methodName),
      Some(className),
      requestString,
      Some(log.message),
      entityString,
      log.databaseTransactionId,
      Some(timeInMilliseconds.asInstanceOf[Double]))

    AppLogger.logAsJson(
      message,
      logLevelType = logLevelType,
      nullableObject = Some(row))

    logTraceRepository.add(row)
  }
}
