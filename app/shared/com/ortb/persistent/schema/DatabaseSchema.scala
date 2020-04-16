package shared.com.ortb.persistent.schema

import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.schema.Tables.{profile, _}
import slick.lifted.TableQuery

class DatabaseSchema(appManager : AppManager) {
  /**
   * Determinate weather to log queries or not.
   */
  lazy val isLogQueries = appManager.config.isLogDatabaseQueryLogs

  /**
   * Advertise available in the system
   */
  lazy val advertises = TableQuery[Advertise]

  /**
   * Auctions held place in the system
   */
  lazy val auctions = TableQuery[Auction]

  /**
   * Banner Advertise Types
   */
  lazy val bannerAdvertiseTypes = TableQuery[Banneradvertisetype]

  /**
   * All the Bid Requests in the system.
   */
  lazy val bidRequests = TableQuery[Bidrequest]
  lazy val bidResponses = TableQuery[Bidresponse]
  lazy val campaigns = TableQuery[Campaign]
  lazy val campaignTargetOperatingSystems = TableQuery[Campaigntargetoperatingsystem]
  lazy val campaignTargetCities = TableQuery[Campaigntargetcity]
  lazy val campaignTargetSites = TableQuery[Campaigntargetsite]

  /**
   * Content types
   */
  lazy val contentCategories = TableQuery[Contentcategory]
  /**
   * Where the video will be displayed as
   */
  lazy val contentContexts = TableQuery[Contentcontext]
  /**
   * Simulation of demand side platforms
   */
  lazy val demandSidePlatforms = TableQuery[Demandsideplatform]
  /**
   * Relationship with geo and advertise
   */
  lazy val geoMappings = TableQuery[Geomapping]
  /**
   * Winning bid info
   */
  lazy val impressions = TableQuery[Impression]
  /**
   * Keywords in the system
   */
  lazy val keywords = TableQuery[Keyword]
  /**
   * Relationship with Keywords and Advertises
   */
  lazy val keywordAdvertiseMappings = TableQuery[Keywordadvertisemapping]
  /**
   * Lost bid info
   */
  lazy val lostBids = TableQuery[Lostbid]
  /**
   * types for no bid responses
   */
  lazy val noBidResponseTypes = TableQuery[Nobidresponsetype]
  /**
   * publisher who (content owner of the advertise)
   */
  lazy val publishers = TableQuery[Publisher]
  /**
   * accounting for how much spend
   */
  lazy val transactions = TableQuery[Transaction]

  lazy val tables = Map(
    advertiseTableName -> advertises,
    auctionTableName -> auctions,
    bannerAdvertiseTypeTableName -> bannerAdvertiseTypes,
    bidRequestTableName -> bidRequests,
    bidResponseTableName -> bidResponses,
    campaignTableName -> campaigns,
    campaignTargetOperatingSystemTableName -> campaignTargetOperatingSystems,
    campaignTargetCityTableName -> campaignTargetCities,
    campaignTargetSiteTableName -> campaignTargetSites,
    contentCategoryTableName -> contentCategories,
    contentContextTableName -> contentContexts,
    demandSidePlatformTableName -> demandSidePlatforms,
    geoMappingTableName -> geoMappings,
    impressionTableName -> impressions,
    keywordTableName -> keywords,
    keywordAdvertiseMappingTableName -> keywordAdvertiseMappings,
    lostBidTableName -> lostBids,
    noBidResponseTypeTableName -> noBidResponseTypes,
    publisherTableName -> publishers,
    transactionTableName -> transactions
  )

  lazy val allSchema : profile.SchemaDescription = Tables.schema
  lazy val advertiseTableName = "Advertise"
  lazy val auctionTableName = "Auction"
  lazy val bannerAdvertiseTypeTableName = "BannerAdvertiseType"
  lazy val bidRequestTableName = "BidRequest"
  lazy val bidResponseTableName = "BidResponse"
  lazy val campaignTableName = "Campaign"
  lazy val campaignTargetOperatingSystemTableName = "CampaignTargetOperatingSystem"
  lazy val campaignTargetCityTableName = "CampaignTargetCity"
  lazy val campaignTargetSiteTableName = "CampaignTargetSite"
  lazy val contentCategoryTableName = "ContentCategory"
  lazy val contentContextTableName = "ContentContext"
  lazy val demandSidePlatformTableName = "DemandSidePlatform"
  lazy val geoMappingTableName = "GeoMapping"
  lazy val impressionTableName = "Impression"
  lazy val keywordTableName = "Keyword"
  lazy val keywordAdvertiseMappingTableName = "KeywordAdvertiseMapping"
  lazy val lostBidTableName = "LostBid"
  lazy val noBidResponseTypeTableName = "NoBidResponseType"
  lazy val publisherTableName = "Publisher"
  lazy val transactionTableName = "Transaction"
  lazy val TableNames = List(
    advertiseTableName,
    auctionTableName,
    bannerAdvertiseTypeTableName,
    bidRequestTableName,
    bidResponseTableName,
    campaignTableName,
    campaignTargetCityTableName,
    campaignTargetSiteTableName,
    campaignTargetOperatingSystemTableName,
    contentCategoryTableName,
    contentContextTableName,
    demandSidePlatformTableName,
    geoMappingTableName,
    impressionTableName,
    keywordTableName,
    keywordAdvertiseMappingTableName,
    lostBidTableName,
    noBidResponseTypeTableName,
    publisherTableName,
    transactionTableName
  )

  def db = appManager.getDb
}