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

class CreativeAttributeRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Creativeattribute, CreativeattributeRow, Int](appManager) {

  override def tableName: String = this.creativeAttributeTableName

  override def getEntityIdFromOptionRow(entity : Option[Tables.CreativeattributeRow]): Int =
    if (entity.isDefined) entity.get.creativeattributeid
    else -1

  override def setEntityIdFromOptionRow(
      entityId: Option[Int],
      entity: Option[Tables.CreativeattributeRow]
  ): Option[Tables.CreativeattributeRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(creativeattributeid = entityId.get))
  }

  override def getAddAction(entity: Tables.CreativeattributeRow)
    : FixedSqlAction[CreativeattributeRow, NoStream, Effect.Write] =
    table returning table.map(_.creativeattributeid) into
      ((entityProjection,
        entityId) => entityProjection.copy(creativeattributeid = entityId)) += entity

  override def table =
    this.creativeAttributes

  override def getDeleteAction(
      entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) =
    table.filter(c => c.creativeattributeid === id)

  override def getAllQuery =
    for { record <- table } yield record

  /**
    * All encoders, decoders and codec for circe
    *
    * @return
    */
  override def encoders: JsonCirceDefaultEncodersImplementation[CreativeattributeRow] =
    new JsonCirceDefaultEncodersImplementation[CreativeattributeRow]()
}
