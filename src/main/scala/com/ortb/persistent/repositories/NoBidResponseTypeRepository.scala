package com.ortb.persistent.repositories

import slick.jdbc.SQLiteProfile.api._
import com.ortb.manager.AppManager
import com.ortb.persistent.repositoryPattern.Repository
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.sql.FixedSqlAction

class NoBidResponseTypeRepository(appManager : AppManager)
  extends
    Repository[Nobidresponsetype, NobidresponsetypeRow, Int](appManager) {

  override def tableName : String = this.noBidResponseTypeTableName

  override def getEntityId(entity : Option[Tables.NobidresponsetypeRow]) : Int =
    if (entity.isDefined) entity.get.nobidresponsetypeid
    else -1

  override def setEntityId(
    entityId : Option[Int],
    entity   : Option[Tables.NobidresponsetypeRow]) : Option[Tables.NobidresponsetypeRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(nobidresponsetypeid = entityId.get))
  }

  override def getAddAction(
    entity : Tables.NobidresponsetypeRow) =
    table returning table.map(_.nobidresponsetypeid) into
      ((entityProjection, entityId) => entityProjection.copy(nobidresponsetypeid = entityId)) += entity

  override def table = this.noBidResponseTypes

  override def getDeleteAction(
    entityId : Int) : FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(
    id : Int) = table.filter(c => c.nobidresponsetypeid === id)

  override def getAllQuery = for {record <- table} yield record
}
