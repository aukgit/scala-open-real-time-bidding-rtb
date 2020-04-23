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
import slick.jdbc.SQLiteProfile
import slick.jdbc.SQLiteProfile.api._
import slick.sql.FixedSqlAction

class CampaignTargetOperatingSystemRepository @Inject()(appManager : AppManager)
  extends RepositoryBase[
    Campaigntargetoperatingsystem,
    CampaigntargetoperatingsystemRow,
    Int
  ](appManager) {

  override def tableName : String = this.campaignTargetOperatingSystemTableName

  override def getEntityId(
    entity : Option[Tables.CampaigntargetoperatingsystemRow]
  ) : Int =
    if (entity.isDefined) entity.get.campaigntargetoperatingsystemid
    else -1

  override def setEntityId(
    entityId : Option[Int],
    entity   : Option[Tables.CampaigntargetoperatingsystemRow]
  ) : Option[Tables.CampaigntargetoperatingsystemRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(campaigntargetoperatingsystemid = entityId.get))
  }

  override def getAddAction(entity : Tables.CampaigntargetoperatingsystemRow) :
  SQLiteProfile.ProfileAction[CampaigntargetoperatingsystemRow, NoStream, Effect.Write] =
    table returning table.map(_.campaigntargetoperatingsystemid) into
      ((
      entityProjection,
      entityId) =>
        entityProjection.copy(campaigntargetoperatingsystemid = entityId)) += entity

  override def table = this.campaignTargetOperatingSystems

  override def getDeleteAction(
    entityId : Int
  ) : FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id : Int) : Query[Campaigntargetoperatingsystem, CampaigntargetoperatingsystemRow, Seq] =
    table.filter(c => c.campaigntargetoperatingsystemid === id)

  override def getAllQuery = for {record <- table} yield record

  /**
   * All encoders, decoders and codec for circe
   *
   * @return
   */
  override def encoders : JsonCirceDefaultEncodersImplementation[CampaigntargetoperatingsystemRow] =
    new JsonCirceDefaultEncodersImplementation[CampaigntargetoperatingsystemRow]()
}
