package shared.com.ortb.persistent.repositories

import com.google.inject.Inject
import slick.jdbc.SQLiteProfile.api._
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.repositories.pattern.RepositoryBase
import shared.com.ortb.persistent.schema.Tables
import shared.com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.sql.FixedSqlAction

class TransactionRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Transaction, TransactionRow, Int](appManager) {

  override def tableName: String = this.transactionTableName

  override def getEntityId(entity: Option[Tables.TransactionRow]): Int =
    if (entity.isDefined) entity.get.transactionid
    else -1

  override def setEntityId(
    entityId: Option[Int],
    entity: Option[Tables.TransactionRow]
  ): Option[Tables.TransactionRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(transactionid = entityId.get))
  }

  override def getAddAction(entity: Tables.TransactionRow) =
    table returning table.map(_.transactionid) into
      ((entityProjection,
        entityId) => entityProjection.copy(transactionid = entityId)) += entity

  override def table = this.transactions

  override def getDeleteAction(
    entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) = table.filter(c => c.transactionid === id)

  override def getAllQuery = for { record <- table } yield record
}
