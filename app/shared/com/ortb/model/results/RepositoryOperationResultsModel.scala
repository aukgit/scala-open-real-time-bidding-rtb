package shared.com.ortb.model.results

import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import shared.com.ortb.constants.AppConstants
import shared.com.ortb.enumeration.LogLevelType
import shared.com.ortb.enumeration.LogLevelType.LogLevelType
import shared.com.ortb.model.attributes.GenericResponseAttributesModel
import shared.com.ortb.model.wrappers.persistent.EntityWrapperModel
import shared.io.helpers.EmptyValidateHelper
import shared.io.jsonParse.implementations.BasicJsonEncoderImplementation
import shared.io.loggers.AppLogger

case class RepositoryOperationResultsModel[TRow, TKey](
  attributes : Option[GenericResponseAttributesModel],
  data : Iterable[EntityWrapperModel[TRow, TKey]]) {
  lazy val hasItems : Boolean = EmptyValidateHelper.hasAnyItemDirect(data)
  lazy val basicEncoderForGenericResponseAttributesModel = new BasicJsonEncoderImplementation[GenericResponseAttributesModel]

  def getLogMessage(additionalMessage : String = "") : String = {
    if (!hasItems) {
      return AppConstants.NoContent
    }

    val message = AppLogger.getLogMessageForEntities(isExecute = true, data, additionalMessage)
    val attributesToJson = basicEncoderForGenericResponseAttributesModel
      .getJsonGenericParser
      .toJsonObjectDirect(attributes.get)

    s"""EntitiesMessage${ AppConstants.LogEqualNewLineWithIndent }
       |$message
       |Attributes${ AppConstants.LogEqualNewLineWithIndent }
       |${ attributesToJson.spaces2 }""".stripMargin
  }

  def logResults(
    additionalMessage : String = "",
    logLevelType : LogLevelType = LogLevelType.INFO) : Unit = {
    if (!hasItems) {
      AppLogger.info(AppConstants.NoContent)
      return
    }

    val message = getLogMessage(additionalMessage)

    AppLogger.additionalLogging(message, logLevelType)
  }
}
