package com.ortb.persistent.repositories

import slick.jdbc.SQLiteProfile.api._
import com.ortb.manager.AppManager
import com.ortb.persistent.repositoryPattern.Repository
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.sql.FixedSqlAction

class CampaignTargetOperatingSystemRepository(appManager : AppManager)
  extends
    Repository[Campaigntargetoperatingsystem, CampaigntargetoperatingsystemRow, Int](appManager) {

  override def tableName : String = this.campaignTargetOperatingSystemTableName

  override def getEntityId(entity : Option[Tables.CampaigntargetoperatingsystemRow]) : Int =
    if (entity.isDefined) entity.get.campaigntargetoperatingsystemid
    else -1

  override def setEntityId(
    entityId : Option[Int],
    entity   : Option[Tables.CampaigntargetoperatingsystemRow]) : Option[Tables.CampaigntargetoperatingsystemRow] = {

    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(campaigntargetoperatingsystemid = entityId.get))
  }

  override def getAddAction(
    entity : Tables.CampaigntargetoperatingsystemRow) =
    table returning table.map(_.campaigntargetoperatingsystemid) into
      ((entityProjection, entityId) => entityProjection.copy(campaigntargetoperatingsystemid = entityId)) += entity

  override def table = this.campaignTargetOperatingSystems

  override def getDeleteAction(
    entityId : Int) : FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(
    id : Int) = table.filter(c => c.campaigntargetoperatingsystemid === id)

  override def getAllQuery = for {record <- table} yield record
}
