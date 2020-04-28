package shared.com.repository

import com.google.inject.Inject
import shared.com.ortb.manager.AppManager
import shared.com.ortb.manager.traits.DefaultExecutionContextManager
import shared.com.ortb.model.config.ConfigModel
import shared.com.ortb.persistent.schema.DatabaseSchema
import shared.com.repository.traits.implementions.adapters.{ RepositoryJsonAdapterImplementation, RepositoryWrapperAdapterImplementation }
import shared.com.repository.traits.implementions.operations.mutations.RepositoryOperationsImplementation
import shared.com.repository.traits.implementions.operations.mutations.async.RepositoryOperationsAsyncImplementation
import shared.com.repository.traits.implementions.operations.queries.SingleRepositoryBaseImplementation
import shared.com.repository.traits.implementions.{ EntityResponseCreatorImplementation, RepositoryRowsToResponseConverterImplementation }
import shared.com.repository.traits.{ FutureToRegular, _ }
import shared.io.jsonParse.implementations.JsonCirceDefaultEncodersImplementation
import shared.io.jsonParse.traits.CirceJsonSupport
import shared.io.loggers.DatabaseLogTracerImplementation

import scala.concurrent.duration._


abstract class RepositoryBase[TTable, TRow, TKey] @Inject()(val appManager : AppManager)
  extends DatabaseSchema(appManager)
    with SingleRepositoryBaseImplementation[TTable, TRow, TKey]
    with EntityResponseCreatorImplementation[TTable, TRow, TKey]
    with DatabaseActionExecutor[TTable, TRow, TKey]
    with RepositoryWrapperAdapterImplementation[TTable, TRow, TKey]
    with RepositoryJsonAdapterImplementation[TTable, TRow, TKey]
    with RepositoryOperationsImplementation[TTable, TRow, TKey]
    with RepositoryOperationsAsyncImplementation[TTable, TRow, TKey]
    with RepositoryRowsToResponseConverterImplementation[TTable, TRow, TKey]
    with RepositoryDatabaseTraceLogger[TRow, TKey]
    with CirceJsonSupport
    with FutureToRegular
    with DefaultExecutionContextManager {

  lazy val appManagerInstance : AppManager = appManager
  lazy val databaseLogger : DatabaseLogTracerImplementation =
    new DatabaseLogTracerImplementation(appManager, this.getClass.getName)

  /**
   * default timeout from config, if < 0 then infinite
   */
  lazy protected implicit val defaultTimeout : Duration =
    if (config != null && config.defaultTimeout < 0) Duration.Inf
    else config.defaultTimeout.seconds

  lazy val config : ConfigModel = appManager.config
  lazy protected val headerMessage : String = s"[$tableName] ->"

  /**
   * All encoders, decoders and codec for circe
   *
   * @return
   */
  def encoders : JsonCirceDefaultEncodersImplementation[TRow]
}
