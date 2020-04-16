package shared.com.ortb.persistent.repositories

import slick.jdbc.SQLiteProfile.api._
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.repositories.pattern.RepositoryBase
import shared.com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.lifted.Query
import slick.sql.FixedSqlAction

class CampaignRepository(appManager: AppManager)
    extends RepositoryBase[Campaign, CampaignRow, Int](appManager) {

  override def tableName: String = this.campaignTableName

  override def getDeleteAction(
    entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  def getQueryById(id: Int): Query[Campaign, CampaignRow, Seq] = {
    table.filter(c => c.campaignid === id)
  }

  override def table = this.campaigns

  override def getAddAction(
    entity: CampaignRow
  ): FixedSqlAction[CampaignRow, NoStream, Effect.Write] =
    table returning table.map(_.campaignid) into
      ((entityProjection,
        entityId) => entityProjection.copy(campaignid = entityId)) += entity

  override def getEntityId(entity: Option[CampaignRow]): Int =
    if (entity.isDefined) entity.get.campaignid
    else -1

  override def setEntityId(entityId: Option[Int],
                           entity: Option[CampaignRow]): Option[CampaignRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(campaignid = entityId.get))
  }

  override def getAllQuery = for { record <- table } yield record
}
