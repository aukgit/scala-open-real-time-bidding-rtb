package shared.com.ortb.persistent.repositories

import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import com.google.inject.Inject
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.schema.Tables
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryBase
import shared.io.jsonParse.implementations.JsonCirceDefaultEncodersImplementation
import slick.dbio.Effect
import slick.jdbc.SQLiteProfile.api._
import slick.sql.FixedSqlAction

class BidRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Bid, BidRow, Int](appManager) {

  override def tableName: String = this.bidTableName

  override def getEntityIdFromOptionRow(entity : Option[Tables.BidRow]): Int =
    if (entity.isDefined) entity.get.bidid
    else -1

  override def setEntityIdFromOptionRow(
      entityId: Option[Int],
      entity: Option[Tables.BidRow]
  ): Option[Tables.BidRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(bidid = entityId.get))
  }

  override def getAddAction(entity: Tables.BidRow)
    : FixedSqlAction[BidRow, NoStream, Effect.Write] =
    table returning table.map(_.bidid) into
      ((entityProjection,
        entityId) => entityProjection.copy(bidid = entityId)) += entity

  override def table =
    this.bids

  override def getDeleteAction(
      entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) =
    table.filter(c => c.bidid === id)

  override def getAllQuery =
    for { record <- table } yield record

  /**
    * All encoders, decoders and codec for circe
    *
    * @return
    */
  override def encoders: JsonCirceDefaultEncodersImplementation[BidRow] =
    new JsonCirceDefaultEncodersImplementation[BidRow]()
}
