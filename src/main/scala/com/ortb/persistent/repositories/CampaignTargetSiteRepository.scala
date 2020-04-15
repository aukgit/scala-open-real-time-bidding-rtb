package com.ortb.persistent.repositories

import slick.jdbc.SQLiteProfile.api._
import com.ortb.manager.AppManager
import com.ortb.persistent.repositoryPattern.Repository
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.sql.FixedSqlAction

class CampaignTargetSiteRepository(appManager : AppManager)
  extends
    Repository[Campaigntargetsite, CampaigntargetsiteRow, Int](appManager) {

  override def tableName : String = this.campaignTargetSiteTableName

  override def getEntityId(entity : Option[Tables.CampaigntargetsiteRow]) : Int =
    if (entity.isDefined) entity.get.campaigntargetsiteid
    else -1

  override def setEntityId(
    entityId : Option[Int],
    entity   : Option[Tables.CampaigntargetsiteRow]) : Option[Tables.CampaigntargetsiteRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(campaigntargetsiteid = entityId.get))
  }

  override def getAddAction(
    entity : Tables.CampaigntargetsiteRow) =
    table returning table.map(_.campaigntargetsiteid) into
      ((entityProjection, entityId) => entityProjection.copy(campaigntargetsiteid = entityId)) += entity

  override def table = this.campaignTargetSites

  override def getDeleteAction(
    entityId : Int) : FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(
    id : Int) = table.filter(c => c.campaigntargetsiteid === id)

  override def getAllQuery = for {record <- table} yield record
}
