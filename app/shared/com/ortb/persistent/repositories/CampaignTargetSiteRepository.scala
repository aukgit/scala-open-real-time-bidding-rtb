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
import shared.io.jsonParse.implementations.JsonCirceDefaultEncodersImplementation
import slick.dbio.Effect
import slick.sql.FixedSqlAction

class CampaignTargetSiteRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Campaigntargetsite, CampaigntargetsiteRow, Int](
      appManager
    ) {

  override def tableName: String = this.campaignTargetSiteTableName

  override def getEntityId(entity: Option[Tables.CampaigntargetsiteRow]): Int =
    if (entity.isDefined) entity.get.campaigntargetsiteid
    else -1

  override def setEntityId(
    entityId: Option[Int],
    entity: Option[Tables.CampaigntargetsiteRow]
  ): Option[Tables.CampaigntargetsiteRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(campaigntargetsiteid = entityId.get))
  }

  override def getAddAction(entity: Tables.CampaigntargetsiteRow) : FixedSqlAction[CampaigntargetsiteRow, NoStream, Effect.Write]
  =
    table returning table.map(_.campaigntargetsiteid) into
      ((entityProjection,
        entityId) =>
         entityProjection.copy(campaigntargetsiteid = entityId)) += entity

  override def table : TableQuery[Campaigntargetsite] = this.campaignTargetSites

  override def getDeleteAction(
    entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) : Query[Campaigntargetsite, CampaigntargetsiteRow, Seq] =
    table.filter(c => c.campaigntargetsiteid === id)

  override def getAllQuery =
    for { record <- table } yield record

  /**
   * All encoders, decoders and codec for circe
   *
   * @return
   */
  override def encoders : JsonCirceDefaultEncodersImplementation[CampaigntargetsiteRow] =
    new JsonCirceDefaultEncodersImplementation[CampaigntargetsiteRow]()
}
