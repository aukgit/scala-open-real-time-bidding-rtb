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

class DemandSidePlatformRepository @Inject()(appManager : AppManager)
  extends RepositoryBase[Demandsideplatform, DemandsideplatformRow, Int](
    appManager
  ) {

  override def tableName : String = this.demandSidePlatformTableName

  override def getEntityId(entity : Option[Tables.DemandsideplatformRow]) : Int =
    if (entity.isDefined) entity.get.demandsideplatformid
    else -1

  override def setEntityId(
    entityId : Option[Int],
    entity   : Option[Tables.DemandsideplatformRow]
  ) : Option[Tables.DemandsideplatformRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(demandsideplatformid = entityId.get))
  }

  override def getAddAction(entity : Tables.DemandsideplatformRow)
  : FixedSqlAction[DemandsideplatformRow, NoStream, Effect.Write] =
    table returning table.map(_.demandsideplatformid) into
      ((
      entityProjection,
      entityId) =>
        entityProjection.copy(demandsideplatformid = entityId)) += entity

  override def table : TableQuery[Demandsideplatform] =
    this.demandSidePlatforms

  override def getDeleteAction(
    entityId : Int
  ) : FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(
    id                        : Int) : Query[Demandsideplatform, DemandsideplatformRow, Seq] =
    table.filter(c => c.demandsideplatformid === id)

  override def getAllQuery
  : Query[Demandsideplatform, DemandsideplatformRow, Seq] =
    for {record <- table} yield record

  /**
   * All encoders, decoders and codec for circe
   *
   * @return
   */
  override def encoders : JsonCirceDefaultEncodersImplementation[DemandsideplatformRow] =
    new JsonCirceDefaultEncodersImplementation[DemandsideplatformRow]()
}
