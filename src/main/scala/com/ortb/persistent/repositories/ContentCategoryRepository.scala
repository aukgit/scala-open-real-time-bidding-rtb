package com.ortb.persistent.repositories

import slick.jdbc.SQLiteProfile.api._
import com.ortb.manager.AppManager
import com.ortb.persistent.repositories.pattern.RepositoryBase
import com.ortb.persistent.schema.Tables
import com.ortb.persistent.schema.Tables._
import slick.dbio.Effect
import slick.sql.FixedSqlAction

class ContentCategoryRepository(appManager: AppManager)
    extends RepositoryBase[Contentcategory, ContentcategoryRow, String](
      appManager
    ) {

  override def tableName: String = this.contentCategoryTableName

  override def getEntityId(entity: Option[Tables.ContentcategoryRow]): String =
    if (entity.isDefined) entity.get.contentcategoryid
    else ""

  override def setEntityId(
    entityId: Option[String],
    entity: Option[Tables.ContentcategoryRow]
  ): Option[Tables.ContentcategoryRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(contentcategoryid = entityId.get))
  }

  override def getAddAction(entity: Tables.ContentcategoryRow) =
    table returning table.map(_.contentcategoryid) into
      ((entityProjection,
        entityId) =>
         entityProjection.copy(contentcategoryid = entityId)) += entity

  override def table = this.contentCategories

  override def getDeleteAction(
    entityId: String
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: String) =
    table.filter(c => c.contentcategoryid === id)

  override def getAllQuery = for { record <- table } yield record
}
