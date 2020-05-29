package shared.com.ortb.persistent.schema

import com.google.inject.Inject
import shared.com.ortb.configuration.ConfigurationExpansion
import shared.com.ortb.manager.AppManager
import shared.com.ortb.persistent.schema.Tables.{ profile, _ }
import slick.lifted.TableQuery

class DatabaseSchema @Inject()(val appManager : AppManager)
  extends ConfigurationExpansion {
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
  lazy val bids = TableQuery[Bid]

  lazy val bidContentCategoriesMappings = TableQuery[Bidcontentcategoriesmapping]

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

  lazy val creativeAttributes = TableQuery[Creativeattribute]

  /**
   * Where the video will be displayed as
   */
  lazy val contentContexts = TableQuery[Contentcontext]
  /**
   * Simulation of demand side platforms
   */
  lazy val demandSidePlatforms = TableQuery[Demandsideplatform]

  lazy val deviceTypes = TableQuery[Devicetype]

  /**
   * Relationship with geo and advertise
   */
  lazy val geoMappings = TableQuery[Geomapping]
  /**
   * Winning bid info
   */
  lazy val impressions = TableQuery[Impression]

  lazy val impressionPlaceholder = TableQuery[Impressionplaceholder]

  /**
   * Keywords in the system
   */
  lazy val keywords = TableQuery[Keyword]
  /**
   * Relationship with Keywords and Advertises
   */
  lazy val keywordAdvertiseMappings = TableQuery[Keywordadvertisemapping]
  lazy val logTraces = TableQuery[Logtrace]

  /**
   * Lost bid info
   */
  lazy val lostBids = TableQuery[Lostbid]
  /**
   * types for no bid responses
   */
  lazy val noBidResponseTypes = TableQuery[Nobidresponsetype]

  lazy val privateMarketPlaceDeals = TableQuery[Privatemarketplacedeal]

  /**
   * publisher who (content owner of the advertise)
   */
  lazy val publishers = TableQuery[Publisher]

  lazy val seatBids = TableQuery[Seatbid]
  /**
   * accounting for how much spend
   */
  lazy val transactions = TableQuery[Transaction]
  lazy val userClassifications = TableQuery[Userclassification]
  lazy val videoPlaybackMethods = TableQuery[Videoplaybackmethod]
  lazy val videoResponseProtocols = TableQuery[Videoresponseprotocol]

  lazy val tables = Map(
    advertiseTableName -> advertises,
    auctionTableName -> auctions,
    bannerAdvertiseTypeTableName -> bannerAdvertiseTypes,
    bidTableName -> bids,
    bidContentCategoriesMappingTableName -> bidContentCategoriesMappings,
    bidRequestTableName -> bidRequests,
    bidResponseTableName -> bidResponses,
    campaignTableName -> campaigns,
    campaignTargetOperatingSystemTableName -> campaignTargetOperatingSystems,
    campaignTargetCityTableName -> campaignTargetCities,
    campaignTargetSiteTableName -> campaignTargetSites,
    contentCategoryTableName -> contentCategories,
    contentContextTableName -> contentContexts,
    creativeAttributeTableName -> creativeAttributes,
    demandSidePlatformTableName -> demandSidePlatforms,
    deviceTypeTableName -> deviceTypes,
    geoMappingTableName -> geoMappings,
    impressionTableName -> impressions,
    impressionPlaceholderTableName -> impressionPlaceholder,
    keywordTableName -> keywords,
    keywordAdvertiseMappingTableName -> keywordAdvertiseMappings,
    logTraceTableName -> logTraces,
    lostBidTableName -> lostBids,
    noBidResponseTypeTableName -> noBidResponseTypes,
    privateMarketPlaceDealTypeTableName -> privateMarketPlaceDeals,
    publisherTableName -> publishers,
    seatBidTableName -> seatBids,
    userClassificationTableName -> userClassifications,
    videoPlaybackMethodTableName -> videoPlaybackMethods,
    videoResponseProtocolTableName -> videoResponseProtocols
  )

  lazy val allSchema : profile.SchemaDescription = Tables.schema
  lazy val advertiseTableName = "Advertise"
  lazy val auctionTableName = "Auction"
  lazy val bannerAdvertiseTypeTableName = "BannerAdvertiseType"
  lazy val bidTableName = "Bid"
  lazy val bidContentCategoriesMappingTableName = "BidContentCategoriesMapping"
  lazy val bidRequestTableName = "BidRequest"
  lazy val bidResponseTableName = "BidResponse"
  lazy val campaignTableName = "Campaign"
  lazy val campaignTargetOperatingSystemTableName = "CampaignTargetOperatingSystem"
  lazy val campaignTargetCityTableName = "CampaignTargetCity"
  lazy val campaignTargetSiteTableName = "CampaignTargetSite"
  lazy val contentCategoryTableName = "ContentCategory"
  lazy val contentContextTableName = "ContentContext"
  lazy val creativeAttributeTableName = "CreativeAttribute"
  lazy val demandSidePlatformTableName = "DemandSidePlatform"
  lazy val deviceTypeTableName = "DeviceType"
  lazy val geoMappingTableName = "GeoMapping"
  lazy val impressionTableName = "Impression"
  lazy val impressionPlaceholderTableName = "ImpressionPlaceholder"
  lazy val keywordTableName = "Keyword"
  lazy val keywordAdvertiseMappingTableName = "KeywordAdvertiseMapping"
  lazy val logTraceTableName = "LogTrace"
  lazy val lostBidTableName = "LostBid"
  lazy val noBidResponseTypeTableName = "NoBidResponseType"
  lazy val privateMarketPlaceDealTypeTableName = "PrivateMarketPlaceDeal"
  lazy val publisherTableName = "Publisher"
  lazy val seatBidTableName = "SeatBid"
  lazy val transactionTableName = "Transaction"
  lazy val userClassificationTableName = "UserClassification"
  lazy val videoPlaybackMethodTableName = "VideoPlaybackMethod"
  lazy val videoResponseProtocolTableName = "VideoResponseProtocol"

  lazy val TableNames = List(
    advertiseTableName,
    auctionTableName,
    bannerAdvertiseTypeTableName,
    bidTableName,
    bidContentCategoriesMappings,
    bidRequestTableName,
    bidResponseTableName,
    campaignTableName,
    campaignTargetCityTableName,
    campaignTargetSiteTableName,
    campaignTargetOperatingSystemTableName,
    contentCategoryTableName,
    contentContextTableName,
    creativeAttributeTableName,
    demandSidePlatformTableName,
    deviceTypeTableName,
    geoMappingTableName,
    impressionTableName,
    impressionPlaceholderTableName,
    keywordTableName,
    keywordAdvertiseMappingTableName,
    logTraceTableName,
    lostBidTableName,
    noBidResponseTypeTableName,
    privateMarketPlaceDealTypeTableName,
    publisherTableName,
    seatBidTableName,
    transactionTableName,
    userClassificationTableName,
    videoPlaybackMethodTableName,
    videoResponseProtocolTableName
  )

  lazy val db = appManager.db
  lazy val views = new DatabaseSchemaViews
}