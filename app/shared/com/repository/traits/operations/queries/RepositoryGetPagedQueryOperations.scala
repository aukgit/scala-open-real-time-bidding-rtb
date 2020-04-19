package shared.com.repository.traits.operations.queries

import akka.stream.InvalidSequenceNumberException
import org.w3c.dom.ranges.RangeException
import shared.com.repository.RepositoryBase
import shared.com.repository.traits.operations.mutations.RepositoryOperationsBase
import shared.io.loggers.AppLogger
import slick.lifted.Query

trait RepositoryGetPagedQueryOperations[TRow]
  extends RepositoryOperationsBase[TRow] {

  def defaultPagedItems = 100

  def limit[TTable2 >: Null <: AnyRef, TRow2](
    query         : Query[TTable2, TRow2, Seq],
    limit         : Int) : Seq[TRow2]

  def getPaged[TTable2 >: Null <: AnyRef, TRow2](
    rows : Query[TTable2, TRow2, Seq],
    pageIndex : Int,
    eachPageItems : Int = defaultPagedItems) : Seq[TRow2]
}
