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
import slick.lifted.Query
import slick.sql.FixedSqlAction

class BannerAdvertiseTypeRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Banneradvertisetype, BanneradvertisetypeRow, Int](
      appManager
    ) {

  override def tableName: String = this.bannerAdvertiseTypeTableName

  override def getEntityId(entity: Option[Tables.BanneradvertisetypeRow]): Int =
    if (entity.isDefined) entity.get.banneradvertisetypeid else -1

  override def setEntityId(
    entityId: Option[Int],
    entity: Option[Tables.BanneradvertisetypeRow]
  ): Option[Tables.BanneradvertisetypeRow] = {

    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(banneradvertisetypeid = entityId.get))
  }

  override def getAddAction(
    entity: Tables.BanneradvertisetypeRow
  ): FixedSqlAction[Tables.BanneradvertisetypeRow, NoStream, Effect.Write] =
    table returning table.map(_.banneradvertisetypeid) into
      ((entityProjection,
        entityId) =>
         entityProjection.copy(banneradvertisetypeid = entityId)) += entity

  override def table = this.bannerAdvertiseTypes

  override def getDeleteAction(
    entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(
    id: Int
  ): Query[Tables.Banneradvertisetype, Tables.BanneradvertisetypeRow, Seq] =
    table.filter(c => c.banneradvertisetypeid === id)

  override def getAllQuery = for { record <- table } yield record

  /**
   * All encoders, decoders and codec for circe
   *
   * @return
   */
  override def encoders : JsonCirceDefaultEncodersImplementation[BanneradvertisetypeRow] = new JsonCirceDefaultEncodersImplementation[BanneradvertisetypeRow]()
}
