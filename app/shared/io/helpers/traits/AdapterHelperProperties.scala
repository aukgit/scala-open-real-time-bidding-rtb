package shared.io.helpers.traits

import shared.com.ortb.adapters.implementations._
import shared.com.ortb.adapters.traits.ModelsAdapters

trait AdapterHelperProperties {
  lazy val entityWrapperAdapter = new EntityWrapperAdapterConcreteImplementation()
  lazy val repositoryAdapter = new RepositoryOperationResultModelAdapterConcreteImplementation()
  lazy val modelsAdapters : ModelsAdapters = new ModelsAdaptersConcreteImplementation
}
