package com.ortb.persistent.repositories

import com.ortb.manager.AppManager
import com.ortb.persistent.schema.DatabaseSchema

class Repositories(appManager : AppManager)
  extends DatabaseSchema(appManager : AppManager) {
  lazy val advertiseRepository = new AdvertiseRepository(appManager)
  lazy val auctionRepository = new AuctionRepository(appManager)
  lazy val bannerAdvertiseTypeRepository = new BannerAdvertiseTypeRepository(appManager)
  lazy val bidRequestRepository = new BidRequestRepository(appManager)
  lazy val bidResponseRepository = new BidResponseRepository(appManager)
  lazy val campaignRepository = new CampaignRepository(appManager)
  lazy val campaignTargetCityRepository = new CampaignTargetCityRepository(appManager)
  lazy val campaignTargetOperatingSystemRepository = new CampaignTargetOperatingSystemRepository(appManager)
  lazy val campaignTargetSiteRepository = new CampaignTargetSiteRepository(appManager)
  lazy val contentCategoryRepository = new ContentCategoryRepository(appManager)
  lazy val contentContextRepository = new ContentContextRepository(appManager)
  lazy val demandSidePlatformRepository = new DemandSidePlatformRepository(appManager)
  lazy val geoMappingRepository = new GeoMappingRepository(appManager)
  lazy val impressionRepository = new ImpressionRepository(appManager)
  lazy val keywordAdvertiseMappingRepository = new KeywordAdvertiseMappingRepository(appManager)
  lazy val keywordRepository = new KeywordRepository(appManager)
  lazy val lostBidRepository = new LostBidRepository(appManager)
  lazy val noBidResponseTypeRepository = new NoBidResponseTypeRepository(appManager)

  /**
   * Holds seat id info
   */
  lazy val publisherRepository = new PublisherRepository(appManager)
  lazy val transactionRepository = new TransactionRepository(appManager)

  /**
   * All the repositories in the system in map Key => TableName
   */
  lazy val repositories = Map(
    advertiseTableName -> advertiseRepository,
    auctionTableName -> auctionRepository,
    bannerAdvertiseTypeTableName -> bannerAdvertiseTypeRepository,
    bidRequestTableName -> bidRequestRepository,
    bidResponseTableName -> bidResponseRepository,
    campaignTableName -> campaignRepository,
    campaignTargetCityTableName -> campaignTargetCityRepository,
    campaignTargetOperatingSystemTableName -> campaignTargetOperatingSystemRepository,
    campaignTargetSiteTableName -> campaignTargetSiteRepository,
    contentCategoryTableName -> contentCategoryRepository,
    contentContextTableName -> contentContextRepository,
    demandSidePlatformTableName -> demandSidePlatformRepository,
    geoMappingTableName -> geoMappingRepository,
    impressionTableName -> impressionRepository,
    keywordAdvertiseMappingTableName -> keywordAdvertiseMappingRepository,
    keywordTableName -> keywordRepository,
    lostBidTableName -> lostBidRepository,
    noBidResponseTypeTableName -> noBidResponseTypeRepository,
    publisherTableName -> publisherRepository,
    transactionTableName -> transactionRepository
  )

}
