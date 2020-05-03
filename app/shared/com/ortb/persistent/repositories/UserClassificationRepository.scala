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

class UserClassificationRepository @Inject()(appManager: AppManager)
    extends RepositoryBase[Userclassification, UserclassificationRow, Int](appManager) {

  override def tableName: String = this.userClassificationTableName

  override def getEntityIdFromOptionRow(entity : Option[Tables.UserclassificationRow]): Int =
    if (entity.isDefined) entity.get.userclassificationid
    else -1

  override def setEntityIdFromOptionRow(
      entityId: Option[Int],
      entity: Option[Tables.UserclassificationRow]
  ): Option[Tables.UserclassificationRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(userclassificationid = entityId.get))
  }

  override def getAddAction(entity: Tables.UserclassificationRow)
    : FixedSqlAction[UserclassificationRow, NoStream, Effect.Write] =
    table returning table.map(_.userclassificationid) into
      ((entityProjection,
        entityId) => entityProjection.copy(userclassificationid = entityId)) += entity

  override def table =
    this.userClassifications

  override def getDeleteAction(
      entityId: Int
  ): FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id: Int) =
    table.filter(c => c.userclassificationid === id)

  override def getAllQuery =
    for { record <- table } yield record

  /**
    * All encoders, decoders and codec for circe
    *
    * @return
    */
  override def encoders: JsonCirceDefaultEncodersImplementation[UserclassificationRow] =
    new JsonCirceDefaultEncodersImplementation[UserclassificationRow]()
}
