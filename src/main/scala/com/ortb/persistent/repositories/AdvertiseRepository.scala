package com.ortb.persistent.repositories

import scala.concurrent._
import slick.jdbc.SQLiteProfile.api._
import com.ortb.manager.AppManager
import com.ortb.persistent.repositoryPattern.Repository
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.lifted.Query
import slick.sql.FixedSqlAction

class AdvertiseRepository(appManager : AppManager)
  extends
    Repository[Advertise, AdvertiseRow, Int](appManager) {

  override def tableName : String = this.advertiseTableName

  override def table = this.advertises

  override def getQueryById(
    id : Int) : Query[Tables.Advertise, Tables.AdvertiseRow, Seq] = table.filter(c => c.advertiseid === id)

  override def getById(
    entityId : Int) : Option[Tables.AdvertiseRow] = {
    val result = this.run(getQueryByIdSingle(entityId).result)
    if (result.isEmpty) {
      return None
    }

    Some(result.head)
  }

  override def getAllAsync : Future[Seq[Tables.AdvertiseRow]] = {
    val query = for {record <- table} yield record
    this.runAsync(query.result)
  }

  override def getIdOf(entity : Option[Tables.AdvertiseRow]) : Int = if (entity.isDefined) entity.get.advertiseid
                                                                     else -1

  override def setEntityId(
    entityId : Option[Int],
    entity   : Option[Tables.AdvertiseRow]) : Option[Tables.AdvertiseRow] = {
    if (entityId.isEmpty || entity.isEmpty) {
      return None
    }

    Some(entity.get.copy(advertiseid = entityId.get))
  }

  override def getAddAction(
    entity : Tables.AdvertiseRow) :
  FixedSqlAction[Tables.AdvertiseRow, NoStream, Effect.Write] =
    table returning table.map(_.advertiseid) into
      ((entityProjection, entityId) => entityProjection.copy(campaignid = entityId)) += entity

  override def getDeleteAction(
    entityId : Int) : FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete
}
