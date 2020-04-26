package shared.io.helpers

import shared.com.ortb.adapters._
import shared.com.ortb.adapters.traits.implementations.BasicAdapterImplementation

object BasicAdapterHelper
  extends BasicAdapterImplementation {
  lazy val entityWrapperAdapter = new EntityWrapperAdapterConcreteImplementation()
  lazy val repositoryAdapter = new RepositoryOperationResultModelAdapterConcreteImplementation()
}