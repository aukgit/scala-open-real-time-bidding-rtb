package shared.com.ortb.adapters.traits

import shared.com.ortb.model.repository.response.{RepositoryOperationResultModel, RepositoryOperationResultsModel}
import shared.com.ortb.model.wrappers.persistent.{EntityWrapper, EntityWrapperWithOptions}

trait BasicAdapter {
  def convertItemTo[A, B](
    item         : Option[A],
    adapterLogic : (Option[A]) => Option[B]) : Option[B]

  def convertItemsTo[A, B](
    items        : Option[Iterable[A]],
    adapterLogic : Option[Iterable[A]] => Option[Iterable[B]]) :
  Option[Iterable[B]]
}



