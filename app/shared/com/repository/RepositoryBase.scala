package shared.com.repository

import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._

import com.google.inject.Inject
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.ConfigModel
import shared.com.ortb.persistent.schema.DatabaseSchema
import shared.com.repository.traits._
import shared.com.repository.traits.implementions.adapters.{RepositoryJsonAdapterImplementation, RepositoryWrapperAdapterImplementation}
import shared.com.repository.traits.implementions.operations.mutations.RepositoryOperationsImplementation
import shared.com.repository.traits.implementions.operations.mutations.async.RepositoryOperationsAsyncImplementation
import shared.com.repository.traits.implementions.operations.queries.SingleRepositoryBaseImplementation
import shared.io.traits.FutureToRegular
import shared.io.traits.jsonParse.JsonCirceDefaultEncoders

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

abstract class RepositoryBase[TTable, TRow, TKey] @Inject()(appManager: AppManager)
    extends DatabaseSchema(appManager)
    with SingleRepositoryBaseImplementation[TTable, TRow, TKey]
    with EntityResponseCreator[TTable, TRow, TKey]
    with DatabaseActionExecutor[TTable, TRow, TKey]
    with RepositoryWrapperAdapterImplementation[TTable, TRow, TKey]
    with RepositoryJsonAdapterImplementation[TTable, TRow, TKey]
    with RepositoryOperationsImplementation[TTable, TRow, TKey]
    with RepositoryOperationsAsyncImplementation[TTable, TRow, TKey]
    with FutureToRegular {

  //noinspection ScalaDeprecation
  lazy protected implicit val executionContext: ExecutionContext =
    appManager.executionContextManager
      .createDefault()
      .prepare()

  /**
    * default timeout from config, if < 0 then infinite
    */
  lazy protected implicit val defaultTimeout: Duration =
    if (config != null && config.defaultTimeout < 0) Duration.Inf
    else config.defaultTimeout.seconds

  lazy protected val config: ConfigModel = appManager.config
  lazy protected val headerMessage: String = s"[$tableName] ->"

  /**
   * All encoders, decoders and codec for circe
   * @return
   */
  def encoders: JsonCirceDefaultEncoders[TRow]
}
