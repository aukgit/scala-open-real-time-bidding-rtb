package services

import com.google.inject.Inject
import services.traits.BasicPersistentServiceOperations
import shared.com.ortb.persistent.Repositories
import shared.com.ortb.persistent.repositories.pattern.RepositoryBase
import shared.com.ortb.persistent.schema.Tables
import shared.com.ortb.persistent.schema.Tables._

class CampaignService @Inject()(repositories: Repositories)
    extends BasicPersistentServiceOperations[Campaign, CampaignRow, Int] {

  val repository: RepositoryBase[Tables.Campaign, Tables.CampaignRow, Int] =
    repositories.campaignRepository

  override def create(): Unit = ???

  override def read(): Unit = ???

  override def getAll: List[Tables.CampaignRow] = ???

  override def update(): Unit = ???

  override def delete(): Unit = ???

  override def getById(id: Int): Tables.CampaignRow = ???
}
