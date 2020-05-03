package shared.com.ortb.persistent

import com.google.inject.Inject
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.repositories.views._
import shared.com.ortb.persistent.schema.DatabaseSchemaViews

class ViewsRepositories @Inject()(appManager: AppManager){
  lazy val bidRelatedIdsViewRepository = new BidRelatedIdsViewRepository(appManager)
  lazy val keywordAdvertiseMappingIdsViewRepository =
    new KeywordAdvertiseMappingIdsViewRepository(appManager)
  lazy val winningPriceInfoViewRepository =
    new WinningPriceInfoViewRepository(appManager)

  lazy private val databaseSchemaViews = new DatabaseSchemaViews

  lazy val repositories = Map(
    databaseSchemaViews.bidRelatedIdsViewName -> bidRelatedIdsViewRepository,
    databaseSchemaViews.keywordAdvertiseMappingIdsViewName -> keywordAdvertiseMappingIdsViewRepository,
    databaseSchemaViews.winningPriceInfoViewName -> winningPriceInfoViewRepository)
}
