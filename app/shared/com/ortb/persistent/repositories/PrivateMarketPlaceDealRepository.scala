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

class PrivateMarketPlaceDealRepository @Inject()(appManager : AppManager)
  extends RepositoryBase[Privatemarketplacedeal, PrivatemarketplacedealRow, Int](appManager) {

  override def tableName : String = this.privateMarketPlaceDealTypeTableName

  override def getEntityIdFromOptionRow(entity : Option[Tables.PrivatemarketplacedealRow]) : Int =
    if (entity.isDefined) entity.get.privatemarketplacedealid
    else -1

  override def setEntityIdFromOptionRow(
    entityId : Option[Int],
    entity : Option[Tables.PrivatemarketplacedealRow]
  ) : Option[Tables.PrivatemarketplacedealRow] = {
    if (isEmptyGivenEntity(entityId, entity)) {
      return None
    }

    Some(entity.get.copy(privatemarketplacedealid = entityId.get))
  }

  override def getAddAction(entity : Tables.PrivatemarketplacedealRow) : FixedSqlAction[PrivatemarketplacedealRow, NoStream, Effect.Write]
  =
    table returning table.map(_.privatemarketplacedealid) into
      ((entityProjection,
        entityId) =>
        entityProjection.copy(privatemarketplacedealid = entityId)) += entity

  override def table = this.privateMarketPlaceDeals

  override def getDeleteAction(
    entityId : Int
  ) : FixedSqlAction[Int, NoStream, Effect.Write] =
    getQueryById(entityId).delete

  override def getQueryById(id : Int) :
  Query[Privatemarketplacedeal, PrivatemarketplacedealRow, Seq] =
    table.filter(c => c.privatemarketplacedealid === id)

  override def getAllQuery =
    for {record <- table} yield record

  /**
   * All encoders, decoders and codec for circe
   *
   * @return
   */
  override def encoders : JsonCirceDefaultEncodersImplementation[PrivatemarketplacedealRow] =
    new JsonCirceDefaultEncodersImplementation[PrivatemarketplacedealRow]()
}
