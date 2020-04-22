package shared.com.ortb.persistent.repositories


import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import com.google.inject.Inject
import slick.jdbc.SQLiteProfile.api._
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.schema.Tables
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryBase
import shared.io.traits.jsonParse.JsonCirceDefaultEncoders
import slick.dbio.Effect
import slick.sql.FixedSqlAction

class BidResponseRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Bidresponse, BidresponseRow, Int](appManager) {

  override def tableName: String = this.bidResponseTableName

  override def getEntityId(entity: Option[Tables.BidresponseRow]): Int =
    if (entity.isDefined) entity.get.bidresponseid else -1

  override def setEntityId(
    entityId: Option[Int],
    entity: Option[Tables.BidresponseRow]
  ): Option[Tables.BidresponseRow] = {

    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(bidresponseid = entityId.get))
  }

  override def getAddAction(entity: Tables.BidresponseRow) =
    table returning table.map(_.bidresponseid) into
      ((entityProjection,
        entityId) => entityProjection.copy(bidresponseid = entityId)) += entity

  override def table = this.bidResponses

  override def getDeleteAction(
    entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) = table.filter(c => c.bidresponseid === id)

  override def getAllQuery = for { record <- table } yield record

  /**
   * All encoders, decoders and codec for circe
   *
   * @return
   */
  override def encoders : JsonCirceDefaultEncoders[BidresponseRow] = new JsonCirceDefaultEncoders[BidresponseRow]()
}
