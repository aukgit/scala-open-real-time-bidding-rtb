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

class SeatBidRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Seatbid, SeatbidRow, Int](appManager) {

  override def tableName: String = this.seatBidTableName

  override def getEntityIdFromOptionRow(entity : Option[Tables.SeatbidRow]): Int =
    if (entity.isDefined) entity.get.seatbidid
    else -1

  override def setEntityIdFromOptionRow(
      entityId: Option[Int],
      entity: Option[Tables.SeatbidRow]
  ): Option[Tables.SeatbidRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(seatbidid = entityId.get))
  }

  override def getAddAction(entity: Tables.SeatbidRow)
    : FixedSqlAction[SeatbidRow, NoStream, Effect.Write] =
    table returning table.map(_.seatbidid) into
      ((entityProjection,
        entityId) => entityProjection.copy(seatbidid = entityId)) += entity

  override def table =
    this.seatBids

  override def getDeleteAction(
      entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) =
    table.filter(c => c.seatbidid === id)

  override def getAllQuery =
    for { record <- table } yield record

  /**
    * All encoders, decoders and codec for circe
    *
    * @return
    */
  override def encoders: JsonCirceDefaultEncodersImplementation[SeatbidRow] =
    new JsonCirceDefaultEncodersImplementation[SeatbidRow]()
}
