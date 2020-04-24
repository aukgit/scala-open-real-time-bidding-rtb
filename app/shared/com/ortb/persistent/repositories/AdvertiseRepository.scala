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
import slick.lifted.Query
import slick.sql.FixedSqlAction

class AdvertiseRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Advertise, AdvertiseRow, Int](appManager) {

  override def tableName: String = this.advertiseTableName

  override def getEntityIdFromOptionRow(entity : Option[Tables.AdvertiseRow]): Int =
    entity.getOrElse(-1).asInstanceOf[Int]

  override def setEntityIdFromOptionRow(
      entityId: Option[Int],
      entity: Option[Tables.AdvertiseRow]
  ): Option[Tables.AdvertiseRow] = {

    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(advertiseid = entityId.get))
  }

  override def getAddAction(
      entity: Tables.AdvertiseRow
  ): FixedSqlAction[Tables.AdvertiseRow, NoStream, Effect.Write] =
    table returning table.map(_.advertiseid) into
      ((entityProjection,
        entityId) => entityProjection.copy(campaignid = entityId)) += entity

  override def table: TableQuery[Advertise] =
    this.advertises

  override def getDeleteAction(
      entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(
      id: Int
  ): Query[Tables.Advertise, Tables.AdvertiseRow, Seq] =
    table.filter(c => c.advertiseid === id)

  override def getAllQuery: Query[Advertise, AdvertiseRow, Seq] =
    for { record <- table } yield record

  /**
    * All encoders, decoders and codec for circe
    *
    * @return
    */
  override def encoders: JsonCirceDefaultEncodersImplementation[AdvertiseRow] =
    new JsonCirceDefaultEncodersImplementation[AdvertiseRow]()
}
