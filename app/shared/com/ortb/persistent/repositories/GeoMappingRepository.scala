package shared.com.ortb.persistent.repositories

import io.circe.generic.semiauto._
import io.circe._
import io.circe.generic.auto._
import com.google.inject.Inject
import slick.jdbc.SQLiteProfile.api._
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.schema.Tables
import shared.com.ortb.persistent.schema.Tables._
import shared.com.repository.RepositoryBase
import shared.io.jsonParse.implementations.JsonCirceDefaultEncodersImplementation
import slick.dbio.Effect
import slick.sql.FixedSqlAction

class GeoMappingRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Geomapping, GeomappingRow, Int](appManager) {

  override def tableName: String = this.geoMappingTableName

  override def getEntityIdFromOptionRow(entity : Option[Tables.GeomappingRow]): Int =
    if (entity.isDefined) entity.get.geomappingid
    else -1

  override def setEntityIdFromOptionRow(
    entityId: Option[Int],
    entity: Option[Tables.GeomappingRow]
  ): Option[Tables.GeomappingRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(geomappingid = entityId.get))
  }

  override def getAddAction(entity: Tables.GeomappingRow) =
    table returning table.map(_.geomappingid) into
      ((entityProjection,
        entityId) => entityProjection.copy(geomappingid = entityId)) += entity

  override def table = this.geoMappings

  override def getDeleteAction(
    entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) = table.filter(c => c.geomappingid === id)

  override def getAllQuery = for { record <- table } yield record

  /**
   * All encoders, decoders and codec for circe
   *
   * @return
   */
  override def encoders : JsonCirceDefaultEncodersImplementation[GeomappingRow] = new JsonCirceDefaultEncodersImplementation[GeomappingRow]()
}
