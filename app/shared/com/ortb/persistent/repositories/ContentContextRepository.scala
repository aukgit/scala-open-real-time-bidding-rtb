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

class ContentContextRepository @Inject()(appManager : AppManager)
  extends RepositoryBase[Contentcontext, ContentcontextRow, Int](appManager) {

  override def tableName : String = this.contentContextTableName

  override def getEntityIdFromOptionRow(entity : Option[Tables.ContentcontextRow]) : Int =
    if (entity.isDefined) entity.get.contentcontextid
    else -1

  override def setEntityIdFromOptionRow(
    entityId : Option[Int],
    entity   : Option[Tables.ContentcontextRow]
  ) : Option[Tables.ContentcontextRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(contentcontextid = entityId.get))
  }

  override def getAddAction(entity : Tables.ContentcontextRow) :
  SQLiteProfile.ProfileAction[ContentcontextRow, NoStream, Effect.Write] =
    table returning table.map(_.contentcontextid) into
      ((
      entityProjection,
      entityId) =>
        entityProjection.copy(contentcontextid = entityId)) += entity

  override def table : TableQuery[Contentcontext] =
    this.contentContexts

  override def getDeleteAction(
    entityId : Int
  ) : FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id : Int) : Query[Contentcontext, ContentcontextRow, Seq] =
    table.filter(c => c.contentcontextid === id)

  override def getAllQuery =
    for {record <- table} yield record

  /**
   * All encoders, decoders and codec for circe
   *
   * @return
   */
  override def encoders : JsonCirceDefaultEncodersImplementation[ContentcontextRow] =
    new JsonCirceDefaultEncodersImplementation[ContentcontextRow]()
}
