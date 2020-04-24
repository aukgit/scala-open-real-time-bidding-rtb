package shared.com.ortb.persistent.repositories


import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import com.google.inject.Inject
import shared.com.ortb.manager.AppManager
import slick.jdbc.SQLiteProfile.api._
import shared.com.ortb.persistent.schema.Tables
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryBase
import shared.io.jsonParse.implementations.JsonCirceDefaultEncodersImplementation
import slick.dbio.Effect
import slick.jdbc.SQLiteProfile
import slick.sql.FixedSqlAction

class CampaignTargetCityRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Campaigntargetcity, CampaigntargetcityRow, Int](
      appManager
    ) {

  override def tableName: String = this.bidResponseTableName

  override def getEntityIdFromOptionRow(entity : Option[Tables.CampaigntargetcityRow]): Int =
    if (entity.isDefined) entity.get.campaigntargetcityid
    else -1

  override def setEntityIdFromOptionRow(
    entityId: Option[Int],
    entity: Option[Tables.CampaigntargetcityRow]
  ): Option[Tables.CampaigntargetcityRow] = {

    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(campaigntargetcityid = entityId.get))
  }

  override def getAddAction(entity: Tables.CampaigntargetcityRow) :
  SQLiteProfile.ProfileAction[CampaigntargetcityRow, NoStream, Effect.Write] =
    table returning table.map(_.campaigntargetcityid) into
      ((entityProjection,
        entityId) =>
         entityProjection.copy(campaigntargetcityid = entityId)) += entity

  override def table : TableQuery[Campaigntargetcity] = 
    this.campaignTargetCities

  override def getDeleteAction(
    entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) : Query[Campaigntargetcity, CampaigntargetcityRow, Seq] =
    table.filter(c => c.campaigntargetcityid === id)

  override def getAllQuery : Query[Campaigntargetcity, CampaigntargetcityRow, Seq] =
    for {record <- table} yield record

  /**
   * All encoders, decoders and codec for circe
   *
   * @return
   */
  override def encoders : JsonCirceDefaultEncodersImplementation[CampaigntargetcityRow] = new JsonCirceDefaultEncodersImplementation[CampaigntargetcityRow]()
}
