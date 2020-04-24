package controllers.v1.apis

import controllers.webapi.core.AbstractRestWebApi
import javax.inject.Inject
import play.api.mvc._
import services.core.AbstractBasicPersistentService
import services._
import shared.com.ortb.persistent.schema.Tables._

class CampaignsApiController @Inject()(
  campaignService : CampaignService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Campaign, CampaignRow, Int](components) {

  override val service : AbstractBasicPersistentService[Campaign, CampaignRow, Int] =
    campaignService
}

class AdvertiseApiController @Inject()(
  injectedService : AdvertiseService,
  components      : ControllerComponents)
  extends AbstractRestWebApi[Advertise, AdvertiseRow, Int](components) {

  override val service = injectedService
}

class AuctionApiController @Inject()(
  injectedService : AuctionService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Auction, AuctionRow, Int](components) {

  override val service = injectedService
}

class BannerAdvertiseTypeApiController @Inject()(
  injectedService : BannerAdvertiseTypeService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Banneradvertisetype,  BanneradvertisetypeRow, Int](components) {

  override val service = injectedService
}

class BidRequestApiController @Inject()(
  injectedService : BidRequestService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Bidrequest,  BidrequestRow, Int](components) {

  override val service = injectedService
}

class BidResponseApiController @Inject()(
  injectedService : BidResponseService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Bidresponse,  BidresponseRow, Int](components) {

  override val service = injectedService
}

class CampaignTargetCityApiController @Inject()(
  injectedService : CampaignTargetCityService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Campaigntargetcity,  CampaigntargetcityRow, Int](components) {

  override val service = injectedService
}

class CampaignTargetOperatingSystemApiController @Inject()(
  injectedService : CampaignTargetOperatingSystemService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Campaigntargetoperatingsystem,  CampaigntargetoperatingsystemRow, Int](components) {

  override val service = injectedService
}

class CampaignTargetSiteSystemApiController @Inject()(
  injectedService : CampaignTargetSiteService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Campaigntargetsite,  CampaigntargetsiteRow, Int](components) {

  override val service = injectedService
}

class ContentCategoryApiController @Inject()(
  injectedService : ContentCategoryService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Contentcategory,  ContentcategoryRow, String](components) {

  override val service = injectedService
}

class ContentContextApiController @Inject()(
  injectedService : ContentContextService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Contentcontext, ContentcontextRow, Int] (components) {

  override val service = injectedService
}

class DemandSidePlatformApiController @Inject()(
  injectedService : DemandSidePlatformService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Demandsideplatform, DemandsideplatformRow, Int] (components) {

  override val service = injectedService
}

class GeoMappingApiController @Inject()(
  injectedService : GeoMappingService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Geomapping, GeomappingRow, Int] (components) {

  override val service = injectedService
}

class ImpressionApiController @Inject()(
  injectedService : ImpressionService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Impression, ImpressionRow, Int] (components) {

  override val service = injectedService
}

class KeywordAdvertiseMappingApiController @Inject()(
  injectedService : KeywordAdvertiseMappingService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Keywordadvertisemapping, KeywordadvertisemappingRow, Int] (components) {

  override val service = injectedService
}

class KeywordApiController @Inject()(
  injectedService : KeywordService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Keyword, KeywordRow, Int]  (components) {

  override val service = injectedService
}


class LostBidApiController @Inject()(
  injectedService : LostBidService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Lostbid, LostbidRow, Int]  (components) {

  override val service = injectedService
}

class NoBidResponseTypeApiController @Inject()(
  injectedService : NoBidResponseTypeService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Nobidresponsetype, NobidresponsetypeRow, Int]  (components) {

  override val service = injectedService
}


class PublisherApiController @Inject()(
  injectedService : PublisherService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Publisher, PublisherRow, Int]  (components) {

  override val service = injectedService
}


class TransactionApiController @Inject()(
  injectedService : TransactionService,
  components : ControllerComponents)
  extends AbstractRestWebApi[Transaction, TransactionRow, Int]   (components) {

  override val service = injectedService
}