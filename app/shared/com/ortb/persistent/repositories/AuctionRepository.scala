package shared.com.ortb.persistent.repositories

import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import com.google.inject.Inject
import slick.jdbc.SQLiteProfile.api._
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryBase
import shared.io.jsonParse.implementations.JsonCirceDefaultEncodersImplementation
import slick.dbio.Effect
import slick.lifted.Query
import slick.sql.FixedSqlAction

class AuctionRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Auction, AuctionRow, Int](appManager) {

  override def tableName: String = this.auctionTableName

  override def getEntityIdFromOptionRow(entity : Option[AuctionRow]): Int =
    if (entity.isDefined) entity.get.auctionid else -1

  override def setEntityIdFromOptionRow(entityId : Option[Int],
                           entity                : Option[AuctionRow]): Option[AuctionRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(auctionid = entityId.get))
  }

  override def getAddAction(
    entity: AuctionRow
  ): FixedSqlAction[AuctionRow, NoStream, Effect.Write] = {
    table returning table.map(_.auctionid) into
      ((entityProjection,
        entityId) => entityProjection.copy(auctionid = entityId)) += entity
  }

  override def getDeleteAction(
    entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int): Query[Auction, AuctionRow, Seq] =
    table.filter(c => c.advertiseid === id)

  override def table = this.auctions

  override def getAllQuery =
    for { record <- table } yield record

  /**
   * All encoders, decoders and codec for circe
   *
   * @return
   */
  override def encoders : JsonCirceDefaultEncodersImplementation[AuctionRow] = new JsonCirceDefaultEncodersImplementation[AuctionRow]()
}
