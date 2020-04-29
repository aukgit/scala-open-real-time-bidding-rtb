package shared.io.helpers

import shared.com.ortb.adapters.traits.{ ModelsAdapters, ModelsAdaptersConcreteImplementation }
import shared.com.ortb.adapters.{ EntityWrapperAdapterConcreteImplementation, RepositoryOperationResultModelAdapterConcreteImplementation }

trait AdapterHelperProperties {
  lazy val entityWrapperAdapter = new EntityWrapperAdapterConcreteImplementation()
  lazy val repositoryAdapter = new RepositoryOperationResultModelAdapterConcreteImplementation()
  lazy val modelsAdapters: ModelsAdapters = new ModelsAdaptersConcreteImplementation
}
