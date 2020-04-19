package shared.com.repository.traits.implementions.operations.queries

import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.queries.RepositoryGetPagedQueryOperations
import shared.io.loggers.AppLogger
import slick.lifted.Query

trait RepositoryGetPagedQueryOperationsImplementation[TTable, TRow, TKey]
  extends RepositoryGetPagedQueryOperations[TRow] {
  this : RepositoryBase[TTable, TRow, TKey] =>

  def limit[TTable2 >: Null <: AnyRef, TRow2](
    query : Query[TTable2, TRow2, Seq],
    limit : Int) : Seq[TRow2] = {
    try {
      this.runAs[AnyRef, TRow2](query)
    } catch {
      case e: Exception => AppLogger.error(e)
    }

    null
  }

  def getPaged[TTable2 >: Null <: AnyRef, TRow2](
    rows : Query[TTable2, TRow2, Seq],
    pageIndex : Int = 1,
    eachPageItems : Int = defaultPagedItems) : Seq[TRow2] = {
    /**
     * eachPageItems = 10
     * pageIndex = 4
     * skipItems = 10 * 4 - 10 = 30
     */
    val skipItems = eachPageItems * pageIndex - eachPageItems //

    if(skipItems <0) {
      throw new Exception(
        "Skipping number cannot be negative. Page Index(>=1) or eachPageItems(>0) has negative sign.")
    }

    try {
      var query:Query[TTable2, TRow2, Seq] = null

      if(skipItems == 0){
        query = rows.take(eachPageItems)
      } else {
        query = rows.drop(skipItems).take(eachPageItems)
      }

      return this.runAs[AnyRef, TRow2](query)
    } catch {
      case e: Exception => AppLogger.errorCaptureAndThrow(e)
    }

    null
  }
}
