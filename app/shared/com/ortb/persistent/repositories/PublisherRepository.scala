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

/**
  * Also known as for seats
  *
  * @param appManager
  */
class PublisherRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Publisher, PublisherRow, Int](appManager) {

  override def tableName: String = this.publisherTableName

  override def getEntityId(entity: Option[Tables.PublisherRow]): Int =
    if (entity.isDefined) entity.get.publisherid
    else -1

  override def setEntityId(
      entityId: Option[Int],
      entity: Option[Tables.PublisherRow]
  ): Option[Tables.PublisherRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(publisherid = entityId.get))
  }

  override def getAddAction(entity: Tables.PublisherRow)
    : FixedSqlAction[PublisherRow, NoStream, Effect.Write] =
    table returning table.map(_.publisherid) into
      ((entityProjection,
        entityId) => entityProjection.copy(publisherid = entityId)) += entity

  override def table: TableQuery[Publisher] = this.publishers

  override def getDeleteAction(
      entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int): Query[Publisher, PublisherRow, Seq] =
    table.filter(c => c.publisherid === id)

  override def getAllQuery: Query[Publisher, PublisherRow, Seq] =
    for { record <- table } yield record

  /**
    * All encoders, decoders and codec for circe
    *
    * @return
    */
  override def encoders: JsonCirceDefaultEncodersImplementation[PublisherRow] =
    new JsonCirceDefaultEncodersImplementation[PublisherRow]()
}
