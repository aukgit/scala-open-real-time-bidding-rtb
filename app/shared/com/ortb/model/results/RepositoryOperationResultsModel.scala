package shared.com.ortb.model.results

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.model.attributes.GenericResponseAttributesModel
import shared.com.ortb.model.wrappers.persistent.EntityWrapperModel
import shared.io.helpers.EmptyValidateHelper
import shared.io.loggers.AppLogger

case class RepositoryOperationResultsModel[TRow, TKey](
  attributes : Option[GenericResponseAttributesModel],
  data : Iterable[EntityWrapperModel[TRow, TKey]]) {
  lazy val hasItems : Boolean = EmptyValidateHelper.isItemsEmptyDirect(data)
  def printResults(additionalMessage: String = ""): Unit = {
    if(!hasItems){
      AppLogger.info(AppConstants.NoContent)
      return
    }

    AppLogger.logEntitiesNonFuture(isExecute = true, data, additionalMessage)
    AppLogger.info(s"Attributes = ${attributes.toString}")
  }
}
