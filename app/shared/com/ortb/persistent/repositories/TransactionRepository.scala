package shared.com.ortb.persistent.repositories

import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import com.google.inject.Inject
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.schema.Tables
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryBase
import shared.io.jsonParse.JsonCirceDefaultEncoders
import slick.dbio.Effect
import slick.jdbc.SQLiteProfile.api._
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

  override def getAddAction(entity: Tables.TransactionRow)
    : FixedSqlAction[TransactionRow, NoStream, Effect.Write] =
    table returning table.map(_.transactionid) into
      ((entityProjection,
        entityId) => entityProjection.copy(transactionid = entityId)) += entity

  override def table: TableQuery[Transaction] =
    this.transactions

  override def getDeleteAction(
      entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int): Query[Transaction, TransactionRow, Seq] =
    table.filter(c => c.transactionid === id)

  override def getAllQuery: Query[Transaction, TransactionRow, Seq] =
    for { record <- table } yield record

  /**
    * All encoders, decoders and codec for circe
    *
    * @return
    */
  override def encoders: JsonCirceDefaultEncoders[TransactionRow] =
    new JsonCirceDefaultEncoders[TransactionRow]()
}
