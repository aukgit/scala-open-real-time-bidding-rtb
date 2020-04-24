package shared.com.repository.traits.implementions

import shared.com.repository.traits.{ RepositoryIdsGetter, SingleRepositoryBase }
import shared.io.helpers.EmptyValidateHelper

trait RepositoryIdsGetterImplementation[TTable, TRow, TKey] extends RepositoryIdsGetter[TTable, TRow, TKey] {
  this : SingleRepositoryBase[TTable, TRow, TKey]  =>

  def getIds(rows: Option[Iterable[TRow]]): Iterable[TKey] = {
    if(EmptyValidateHelper.isItemsEmpty(rows)){
      return null
    }

    rows.get.map(row => getEntityId(row))
  }

  def getIdsAsString(rows: Option[Iterable[TRow]]): Iterable[String] = {
    val ids = getIds(rows)

    if(EmptyValidateHelper.isItemsEmpty(Some(ids))){
      return null
    }

    ids.map(id => id.toString)
  }

  def getRowToIdsAsString(row: Option[TRow]): Iterable[String] = {
    if(EmptyValidateHelper.isEmpty(row)){
      return Seq("")
    }

    Seq(getEntityIdFromOptionRow(row).toString)
  }
}
