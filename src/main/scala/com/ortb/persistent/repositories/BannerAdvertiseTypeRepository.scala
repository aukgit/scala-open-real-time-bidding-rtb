package com.ortb.persistent.repositories

import slick.jdbc.SQLiteProfile.api._
import com.ortb.manager.AppManager
import com.ortb.persistent.repositoryPattern.Repository
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.lifted.Query
import slick.sql.FixedSqlAction

class BannerAdvertiseTypeRepository(appManager : AppManager)
  extends
    Repository[Banneradvertisetype, BanneradvertisetypeRow, Int](appManager) {

  override def tableName : String = this.bannerAdvertiseTypeTableName

  override def getEntityId(entity : Option[Tables.BanneradvertisetypeRow]) : Int =
    if(entity.isDefined) entity.get.banneradvertisetypeid else -1

  override def setEntityId(
    entityId : Option[Int],
    entity   : Option[Tables.BanneradvertisetypeRow]) : Option[Tables.BanneradvertisetypeRow] = {

    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(banneradvertisetypeid = entityId.get))
  }

  override def getAddAction(
    entity : Tables.BanneradvertisetypeRow) :
  FixedSqlAction[Tables.BanneradvertisetypeRow, NoStream, Effect.Write] =
    table returning table.map(_.banneradvertisetypeid) into
      ((entityProjection, entityId) => entityProjection.copy(banneradvertisetypeid = entityId)) += entity

  override def table = this.bannerAdvertiseTypes

  override def getDeleteAction(
    entityId : Int) : FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(
    id : Int) : Query[Tables.Banneradvertisetype, Tables.BanneradvertisetypeRow, Seq] = table.filter(c => c.banneradvertisetypeid === id)

  override def getAllQuery = for {record <- table} yield record
}
