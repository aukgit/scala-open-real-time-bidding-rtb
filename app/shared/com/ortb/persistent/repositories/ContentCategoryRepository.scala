package shared.com.ortb.persistent.repositories

import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.schema.Tables
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryBase
import shared.io.jsonParse.JsonCirceDefaultEncoders
import slick.jdbc.SQLiteProfile.api._
import slick.dbio.{ Effect, NoStream }
import slick.sql.FixedSqlAction

class ContentCategoryRepository(appManager: AppManager)
    extends RepositoryBase[Contentcategory, ContentcategoryRow, String](
      appManager) {

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

  override def getAddAction(entity: Tables.ContentcategoryRow)
    : FixedSqlAction[ContentcategoryRow, NoStream, Effect.Write] =
    table returning table.map(_.contentcategoryid) into
      ((entityProjection,
        entityId) =>
         entityProjection.copy(contentcategoryid = entityId)) += entity

  override def table: TableQuery[Contentcategory] = this.contentCategories

  override def getDeleteAction(
      entityId: String
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(
      id: String): Query[Contentcategory, ContentcategoryRow, Seq] =
    table.filter(c => c.contentcategoryid === id)

  override def getAllQuery =
    for {
      record <- table
    } yield record

  /**
    * All encoders, decoders and codec for circe
    *
    * @return
    */
  override def encoders: JsonCirceDefaultEncoders[ContentcategoryRow] =
    new JsonCirceDefaultEncoders[ContentcategoryRow]()
}
