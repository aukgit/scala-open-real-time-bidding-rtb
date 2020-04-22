package shared.com.repository.traits

import shared.com.ortb.enumeration.DatabaseActionType.DatabaseActionType
import shared.com.ortb.model.repository.GenericResponseAttributesModel
import shared.com.ortb.model.repository.response.RepositoryOperationResultModel
import shared.com.ortb.model.wrappers.persistent.EntityWrapper
import shared.com.repository.RepositoryBase
import shared.io.helpers.BasicAdapterHelper
import shared.io.loggers.AppLogger

import scala.concurrent.Future

trait EntityResponseCreator[TTable, TRow, TKey] {
  protected def createResponseForAffectedRowCount(
    affectedRow : Int,
    entity     : Option[TRow],
    actionType : DatabaseActionType,
    message    : String = "",
    isSuccess  : Boolean = true
  ) : RepositoryOperationResultModel[TRow, TKey]

  protected def createResponseForAffectedRow(
    affectedEntity : Option[TRow],
    affectedRowsCount : Option[Int],
    actionType : DatabaseActionType,
    message : String = "",
    isSuccess : Boolean = true
  ) : RepositoryOperationResultModel[TRow, TKey]

  def getEmptyResponseFor(actionType : DatabaseActionType) : RepositoryOperationResultModel[TRow, TKey]

  protected def createResponseFor(
    entityId : Option[TKey],
    entity : Option[TRow],
    actionType : DatabaseActionType,
    message : String = "",
    isSuccess : Boolean = true
  ) : RepositoryOperationResultModel[TRow, TKey]

  protected def getMessageForEntity(
    affectedRowsCount : Option[Int],
    actionType        : DatabaseActionType,
    message           : String): String

  protected def getEmptyResponseForInFuture(
    actionType : DatabaseActionType
  ) : Future[RepositoryOperationResultModel[TRow, TKey]]
}


