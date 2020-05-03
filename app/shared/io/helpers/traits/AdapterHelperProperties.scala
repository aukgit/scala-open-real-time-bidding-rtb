package shared.io.helpers.traits

import shared.com.ortb.adapters.implementations.{ EntityWrapperAdapterConcreteImplementation, ModelsAdaptersConcreteImplementation, RepositoryOperationResultModelAdapterConcreteImplementation }
import shared.com.ortb.adapters.traits.ModelsAdapters
import shared.com.ortb.adapters.RepositoryOperationResultModelAdapterConcreteImplementation

trait AdapterHelperProperties {
  lazy val entityWrapperAdapter = new EntityWrapperAdapterConcreteImplementation()
  lazy val repositoryAdapter = new RepositoryOperationResultModelAdapterConcreteImplementation()
  lazy val modelsAdapters: ModelsAdapters = new ModelsAdaptersConcreteImplementation
}
