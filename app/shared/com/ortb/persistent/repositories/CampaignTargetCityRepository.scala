package shared.com.ortb.persistent.repositories

import slick.jdbc.SQLiteProfile.api._
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.repositories.pattern.RepositoryBase
import shared.com.ortb.persistent.schema.Tables
import shared.com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.sql.FixedSqlAction

class CampaignTargetCityRepository(appManager: AppManager)
    extends RepositoryBase[Campaigntargetcity, CampaigntargetcityRow, Int](
      appManager
    ) {

  override def tableName: String = this.bidResponseTableName

  override def getEntityId(entity: Option[Tables.CampaigntargetcityRow]): Int =
    if (entity.isDefined) entity.get.campaigntargetcityid
    else -1

  override def setEntityId(
    entityId: Option[Int],
    entity: Option[Tables.CampaigntargetcityRow]
  ): Option[Tables.CampaigntargetcityRow] = {

    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(campaigntargetcityid = entityId.get))
  }

  override def getAddAction(entity: Tables.CampaigntargetcityRow) =
    table returning table.map(_.campaigntargetcityid) into
      ((entityProjection,
        entityId) =>
         entityProjection.copy(campaigntargetcityid = entityId)) += entity

  override def table = this.campaignTargetCities

  override def getDeleteAction(
    entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) =
    table.filter(c => c.campaigntargetcityid === id)

  override def getAllQuery = for { record <- table } yield record
}
