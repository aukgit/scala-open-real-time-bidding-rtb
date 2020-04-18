package shared.com.ortb.persistent.repositories.pattern

import com.google.inject.Inject
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.ConfigModel
import shared.com.ortb.persistent.repositories.pattern.adapters.RepositoryWrapperAdapterImplementation
import shared.com.ortb.persistent.repositories.pattern.traits._
import shared.com.ortb.persistent.schema.DatabaseSchema
import shared.io.traits.FutureToRegular

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

abstract class RepositoryBase[TTable, TRow, TKey] @Inject()(appManager: AppManager)
    extends DatabaseSchema(appManager)
    with SingleRepositoryBase[TTable, TRow, TKey]
    with EntityResponseCreator[TTable, TRow, TKey]
    with DatabaseActionExecutor[TTable, TRow, TKey]
    with RepositoryWrapperAdapterImplementation[TTable, TRow, TKey]
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
}
