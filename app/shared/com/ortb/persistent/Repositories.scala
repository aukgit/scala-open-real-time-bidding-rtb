package shared.com.ortb.persistent

import com.google.inject.Inject
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.repositories.views.{ BidRelatedIdsViewRepository, KeywordAdvertiseMappingIdsViewRepository, WinningPriceInfoViewRepository }
import shared.com.ortb.persistent.repositories.{ BidContentCategoriesMappingRepository, BidRepository, CreativeAttributeRepository, DeviceTypeRepository, PrivateMarketPlaceDealRepository, SeatBidRepository, UserClassificationRepository, VideoPlaybackMethodRepository, VideoResponseProtocolRepository, _ }
import shared.com.ortb.persistent.schema.{ DatabaseSchema, DatabaseSchemaViews }

class Repositories @Inject()(appManager: AppManager)
    extends DatabaseSchema(appManager: AppManager) {
  lazy val advertiseRepository = new AdvertiseRepository(appManager)
  lazy val auctionRepository = new AuctionRepository(appManager)
  lazy val bannerAdvertiseTypeRepository =
    new BannerAdvertiseTypeRepository(appManager)

  lazy val bidRepository = new BidRepository(appManager)
  lazy val bidRequestRepository = new BidRequestRepository(appManager)
  lazy val bidResponseRepository = new BidResponseRepository(appManager)
  lazy val bidContentCategoriesMappingRepository = new BidContentCategoriesMappingRepository(appManager)
  lazy val campaignRepository = new CampaignRepository(appManager)
  lazy val campaignTargetCityRepository =
    new CampaignTargetCityRepository(appManager)

  lazy val campaignTargetOperatingSystemRepository =
    new CampaignTargetOperatingSystemRepository(appManager)

  lazy val campaignTargetSiteRepository =
    new CampaignTargetSiteRepository(appManager)

  lazy val contentCategoryRepository = new ContentCategoryRepository(appManager)
  lazy val contentContextRepository = new ContentContextRepository(appManager)
  lazy val creativeAttributeRepository = new CreativeAttributeRepository(appManager)
  lazy val demandSidePlatformRepository =
    new DemandSidePlatformRepository(appManager)
  lazy val deviceTypeRepository =
    new DeviceTypeRepository(appManager)
  lazy val geoMappingRepository = new GeoMappingRepository(appManager)
  lazy val impressionRepository = new ImpressionRepository(appManager)
  lazy val keywordAdvertiseMappingRepository =
    new KeywordAdvertiseMappingRepository(appManager)
  lazy val keywordRepository = new KeywordRepository(appManager)
  lazy val logTraceRepository = new LogTraceRepository(appManager)
  lazy val lostBidRepository = new LostBidRepository(appManager)
  lazy val noBidResponseTypeRepository =
    new NoBidResponseTypeRepository(appManager)
  lazy val privateMarketPlaceDealRepository =
    new PrivateMarketPlaceDealRepository(appManager)
  /**
    * Holds seat id info
    */
  lazy val publisherRepository = new PublisherRepository(appManager)
  lazy val seatBidRepository = new SeatBidRepository(appManager)
  lazy val transactionRepository = new TransactionRepository(appManager)
  lazy val userClassificationRepository = new UserClassificationRepository(appManager)
  lazy val videoPlaybackMethodRepository = new VideoPlaybackMethodRepository(appManager)
  lazy val videoResponseProtocolRepository = new VideoResponseProtocolRepository(appManager)

  /**
    * All the repositories in the system in map Key => TableName
    */
  lazy val repositories = Map(
    advertiseTableName -> advertiseRepository,
    auctionTableName -> auctionRepository,
    bannerAdvertiseTypeTableName -> bannerAdvertiseTypeRepository,
    bidTableName -> bidRepository,
    bidRequestTableName -> bidRequestRepository,
    bidResponseTableName -> bidResponseRepository,
    campaignTableName -> campaignRepository,
    campaignTargetCityTableName -> campaignTargetCityRepository,
    campaignTargetOperatingSystemTableName -> campaignTargetOperatingSystemRepository,
    campaignTargetSiteTableName -> campaignTargetSiteRepository,
    contentCategoryTableName -> contentCategoryRepository,
    contentContextTableName -> contentContextRepository,
    creativeAttributeTableName -> creativeAttributeRepository,
    demandSidePlatformTableName -> demandSidePlatformRepository,
    deviceTypeTableName -> deviceTypeRepository,
    geoMappingTableName -> geoMappingRepository,
    impressionTableName -> impressionRepository,
    keywordTableName -> keywordRepository,
    keywordAdvertiseMappingTableName -> keywordAdvertiseMappingRepository,
    logTraceTableName -> logTraceRepository,
    lostBidTableName -> lostBidRepository,
    noBidResponseTypeTableName -> noBidResponseTypeRepository,
    privateMarketPlaceDealTypeTableName -> privateMarketPlaceDealRepository,
    publisherTableName -> publisherRepository,
    seatBidTableName -> seatBidRepository,
    transactionTableName -> transactionRepository,
    userClassificationTableName -> userClassificationRepository,
    videoPlaybackMethodTableName -> videoPlaybackMethodRepository,
    videoResponseProtocolTableName -> videoResponseProtocolRepository,
  )

  lazy val viewsRepositories = new ViewsRepositories(appManager)
}
