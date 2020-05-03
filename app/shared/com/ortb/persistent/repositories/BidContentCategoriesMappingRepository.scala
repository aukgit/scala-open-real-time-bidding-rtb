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

class BidContentCategoriesMappingRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Bidcontentcategoriesmapping, BidcontentcategoriesmappingRow, Int](appManager) {

  override def tableName: String = this.bidContentCategoriesMappingTableName

  override def getEntityIdFromOptionRow(entity : Option[Tables.BidcontentcategoriesmappingRow]): Int =
    if (entity.isDefined) entity.get.bidcontentcategoriesmappingid
    else -1

  override def setEntityIdFromOptionRow(
      entityId: Option[Int],
      entity: Option[Tables.BidcontentcategoriesmappingRow]
  ): Option[Tables.BidcontentcategoriesmappingRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(bidcontentcategoriesmappingid = entityId.get))
  }

  override def getAddAction(entity: Tables.BidcontentcategoriesmappingRow)
    : FixedSqlAction[BidcontentcategoriesmappingRow, NoStream, Effect.Write] =
    table returning table.map(_.bidcontentcategoriesmappingid) into
      ((entityProjection,
        entityId) => entityProjection.copy(bidcontentcategoriesmappingid = entityId)) += entity

  override def table =
    this.bidContentCategoriesMappings

  override def getDeleteAction(
      entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) =
    table.filter(c => c.bidcontentcategoriesmappingid === id)

  override def getAllQuery =
    for { record <- table } yield record

  /**
    * All encoders, decoders and codec for circe
    *
    * @return
    */
  override def encoders: JsonCirceDefaultEncodersImplementation[BidcontentcategoriesmappingRow] =
    new JsonCirceDefaultEncodersImplementation[BidcontentcategoriesmappingRow]()
}
