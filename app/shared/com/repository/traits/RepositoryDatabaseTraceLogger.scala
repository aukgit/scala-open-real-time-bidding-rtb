package shared.com.repository.traits

import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.enumeration.LogLevelType
import shared.com.ortb.enumeration.LogLevelType.LogLevelType
import shared.com.ortb.model.LogTraceModel
import shared.com.ortb.model.results.{ RepositoryOperationResultModel, RepositoryOperationResultsModel }
import shared.io.loggers.DatabaseLogTracerImplementation

trait RepositoryDatabaseTraceLogger[TRow, TKey] {
  val databaseLogger : DatabaseLogTracerImplementation
  protected val headerMessage : String

  def tracePrePost(
    isLogQueries : Boolean,
    methodName : String,
    preRow : Option[TRow],
    completeRow : Option[TRow],
    databaseActionType : DatabaseActionType,
    logLevelType : LogLevelType = LogLevelType.DEBUG) : Unit = {
    if (!isLogQueries) {
      return
    }

    val logModel = LogTraceModel(
      methodName,
      Some(preRow),
      s"[$headerMessage] [${ databaseActionType.toString }]",
      entity = Some(completeRow))

    databaseLogger.trace(logModel, logLevelType = logLevelType)
  }

  def trace(
    isLogQueries : Boolean,
    methodName : String,
    resultModel : Option[RepositoryOperationResultModel[TRow, TKey]],
    databaseActionType : DatabaseActionType,
    logLevelType : LogLevelType = LogLevelType.DEBUG) : Unit = {
    if (!isLogQueries) {
      return
    }

    val logModel = LogTraceModel(
      methodName,
      resultModel,
      s"[$headerMessage] [${ databaseActionType.toString }]",
      entity = resultModel.get.data)

    databaseLogger.trace(logModel, logLevelType = logLevelType)
  }

  def traceResults(
    isLogQueries : Boolean,
    methodName : String,
    resultsModel : Option[RepositoryOperationResultsModel[TRow, TKey]],
    databaseActionType : DatabaseActionType,
    logLevelType : LogLevelType = LogLevelType.DEBUG) : Unit = {
    if (!isLogQueries) {
      return
    }

    val logModel = LogTraceModel(
      methodName,
      Some(resultsModel),
      s"[$headerMessage] [${ databaseActionType.toString }]",
      entity = None,
      entities = Some(resultsModel.get.data))

    databaseLogger.trace(logModel, logLevelType = logLevelType)
  }
}
