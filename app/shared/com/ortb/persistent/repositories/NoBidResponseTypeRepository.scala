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
import slick.jdbc.SQLiteProfile.api._
import slick.sql.FixedSqlAction

class NoBidResponseTypeRepository @Inject()(appManager : AppManager)
  extends RepositoryBase[Nobidresponsetype, NobidresponsetypeRow, Int](
    appManager
  ) {

  override def tableName : String = this.noBidResponseTypeTableName

  override def getEntityIdFromOptionRow(entity : Option[Tables.NobidresponsetypeRow]) : Int =
    if (entity.isDefined) entity.get.nobidresponsetypeid
    else -1

  override def setEntityIdFromOptionRow(
    entityId : Option[Int],
    entity : Option[Tables.NobidresponsetypeRow]
  ) : Option[Tables.NobidresponsetypeRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(nobidresponsetypeid = entityId.get))
  }

  override def getAddAction(entity : Tables.NobidresponsetypeRow)
  : FixedSqlAction[NobidresponsetypeRow, NoStream, Effect.Write] =
    table returning table.map(_.nobidresponsetypeid) into
      ((
      entityProjection,
      entityId) =>
        entityProjection.copy(nobidresponsetypeid = entityId)) += entity

  override def table : TableQuery[Nobidresponsetype] = this.noBidResponseTypes

  override def getDeleteAction(
    entityId : Int
  ) : FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(
    id : Int) : Query[Nobidresponsetype, NobidresponsetypeRow, Seq] =
    table.filter(c => c.nobidresponsetypeid === id)

  override def getAllQuery
  : Query[Nobidresponsetype, NobidresponsetypeRow, Seq] =
    for {record <- table} yield record

  /**
   * All encoders, decoders and codec for circe
   *
   * @return
   */
  override def encoders : JsonCirceDefaultEncodersImplementation[NobidresponsetypeRow] =
    new JsonCirceDefaultEncodersImplementation[NobidresponsetypeRow]()
}
