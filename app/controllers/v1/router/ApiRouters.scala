package controllers.v1.router

import controllers.controllerRoutes.routerGeneric.AbstractGenericRouter
import controllers.v1.apis._
import javax.inject.Inject
import shared.com.ortb.persistent.schema.Tables._

class CampaignApiRouter @Inject()(
  injectedApiController  : CampaignsApiController) extends
  AbstractGenericRouter[Campaign, CampaignRow, Int](injectedApiController)

class AdvertiseApiRouter @Inject()(
  injectedApiController : AdvertiseApiController) extends
  AbstractGenericRouter[Advertise, AdvertiseRow, Int](injectedApiController)

class AuctionApiRouter @Inject()(
  injectedApiController : AuctionApiController) extends
  AbstractGenericRouter[Auction, AuctionRow, Int](injectedApiController)

class BannerAdvertiseTypeApiRouter @Inject()(
  injectedApiController : BannerAdvertiseTypeApiController) extends
  AbstractGenericRouter[Banneradvertisetype, BanneradvertisetypeRow, Int](injectedApiController)

class BidRequestApiRouter @Inject()(
  injectedApiController : BidRequestApiController) extends
  AbstractGenericRouter[Bidrequest, BidrequestRow, Int](injectedApiController)

class BidResponseApiRouter @Inject()(
  injectedApiController : BidResponseApiController) extends
  AbstractGenericRouter[Bidresponse, BidresponseRow, Int](injectedApiController)

class CampaignTargetCityApiRouter @Inject()(
  injectedApiController : CampaignTargetCityApiController) extends
  AbstractGenericRouter[Campaigntargetcity, CampaigntargetcityRow, Int](injectedApiController)

class CampaignTargetOperatingSystemApiRouter @Inject()(
  injectedApiController : CampaignTargetOperatingSystemApiController) extends
  AbstractGenericRouter[Campaigntargetoperatingsystem, CampaigntargetoperatingsystemRow, Int](injectedApiController)

class CampaignTargetSiteSystemApiRouter @Inject()(
  injectedApiController : CampaignTargetSiteSystemApiController) extends
  AbstractGenericRouter[Campaigntargetsite, CampaigntargetsiteRow, Int](injectedApiController)

class ContentCategoryApiRouter @Inject()(
  injectedApiController : ContentCategoryApiController) extends
  AbstractGenericRouter[Contentcategory, ContentcategoryRow, String](injectedApiController)

class ContentContextApiRouter @Inject()(
  injectedApiController : ContentContextApiController) extends
  AbstractGenericRouter[Contentcontext, ContentcontextRow, Int](injectedApiController)

class DemandSidePlatformApiRouter @Inject()(
  injectedApiController : DemandSidePlatformApiController) extends
  AbstractGenericRouter[Demandsideplatform, DemandsideplatformRow, Int](injectedApiController)

class GeoMappingApiRouter @Inject()(
  injectedApiController : GeoMappingApiController) extends
  AbstractGenericRouter[Geomapping, GeomappingRow, Int](injectedApiController)

class ImpressionApiRouter @Inject()(
  injectedApiController : ImpressionApiController) extends
  AbstractGenericRouter[Impression, ImpressionRow, Int](injectedApiController)

class KeywordAdvertiseMappingApiRouter @Inject()(
  injectedApiController : KeywordAdvertiseMappingApiController) extends
  AbstractGenericRouter[Keywordadvertisemapping, KeywordadvertisemappingRow, Int](injectedApiController)

class KeywordApiRouter @Inject()(
  injectedApiController : KeywordApiController) extends
  AbstractGenericRouter[Keyword, KeywordRow, Int](injectedApiController)

class LostBidApiRouter @Inject()(
  injectedApiController : LostBidApiController) extends
  AbstractGenericRouter[Lostbid, LostbidRow, Int](injectedApiController)

class NoBidResponseTypeApiRouter @Inject()(
  injectedApiController : NoBidResponseTypeApiController) extends
  AbstractGenericRouter[Nobidresponsetype, NobidresponsetypeRow, Int](injectedApiController)

class PublisherApiRouter @Inject()(
  injectedApiController : PublisherApiController) extends
  AbstractGenericRouter[Publisher, PublisherRow, Int](injectedApiController)

class TransactionApiRouter @Inject()(
  injectedApiController : TransactionApiController) extends
  AbstractGenericRouter[Transaction, TransactionRow, Int](injectedApiController)