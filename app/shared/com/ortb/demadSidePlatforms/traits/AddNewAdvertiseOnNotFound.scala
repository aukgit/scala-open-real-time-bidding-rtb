package shared.com.ortb.demadSidePlatforms.traits

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.demadSidePlatforms.DemandSidePlatformBiddingAgent
import shared.com.ortb.model.auctionbid.ImpressionBiddableInfoModel
import shared.com.ortb.model.results.DemandSidePlatformBiddingRequestWrapperModel
import shared.com.ortb.persistent.schema.Tables._
import shared.io.loggers.AppLogger
import slick.jdbc.SQLiteProfile.api._

trait AddNewAdvertiseOnNotFound {
  this : DemandSidePlatformBiddingAgent =>

  def addNewAdvertiseIfNoAdvertiseInTheGivenCriteria(
    request : DemandSidePlatformBiddingRequestWrapperModel,
    isEmpty : Boolean,
    impressionBiddableInfoModel : ImpressionBiddableInfoModel) : Unit = {
    if (!demandSidePlatformConfiguration.isAddNewAdvertiseOnNotFound || !isEmpty) {
      return
    }

    // create DSP -> campaign -> Advertise
    val methodName = "addNewAdvertiseIfNoAdvertiseInTheGivenCriteria"
    AppLogger.debug(methodName, s"Adding Advertise as per configuration, ${ request.bidRequestModel.toString }")

    val demandSideId = coreProperties.demandSideId
    val repositories = coreProperties.repositories
    val publishersRepository = repositories.publisherRepository
    val campaignRepository = repositories.campaignRepository
    val advertiseRepository = repositories.advertiseRepository

    val publisherByDspQuery = publishersRepository
      .getAllQuery
      .filter(w => w.demandsideplatformid === coreProperties.demandSideId)
      .take(1)
      .result

    val impressionModel = impressionBiddableInfoModel.impression
    val bidReq = request.bidRequestModel
    val site = bidReq.site
    val publisher = publishersRepository
      .getFirstOrDefaultFromQuery(publisherByDspQuery)

    // create campaign
    val categoryId = modelsAdapters.siteModelAdapter.getCategoryId(site)
    val bannerString = impressionModel.banner.get.toString
    val simpleBanner = bannerModelAdapter.getSimpleBanner(impressionModel.banner.get)
    val budget : Double = 1500
    val publisherId = publisher.get.publisherid
    val campaignRow = CampaignRow(
      -1,
      s"Generated: Campaign - Banner($bannerString)",
      categoryId,
      budget,
      0,
      budget,
      AppConstants.EmptyDoubleOption,
      AppConstants.EmptyDoubleOption,
      0,
      publisherid = Some(publisherId),
      demandsideplatformid = demandSideId,
      0,
      999,
      0,
      None,
      None,
      None)

    val campaignCreated = campaignRepository.add(campaignRow)
    val campaignId = campaignCreated.getIdAsInt
    val contextTextId = 5

    val advertise = AdvertiseRow(
      advertiseid = -1,
      campaignid = campaignId.get,
      1,
      s"Generated Banner Advertise($bannerString)",
      Some(contextTextId),
      s"BidUrl for $bannerString",
      Some(s"IFrame for $bannerString"),
      0,
      isvideo = 0,
      impressioncount = 0,
      height = simpleBanner.h,
      isbanner = Some(1),
      width = simpleBanner.w,
      minheight = simpleBanner.hmin,
      minwidth = simpleBanner.wmin,
      maxheight = simpleBanner.hmax,
      maxwidth = simpleBanner.wmax,
      hasagerestriction = 0,
      minage = Some(0),
      maxage = Some(0),
      createddate = Some(0))

    advertiseRepository.add(advertise)
    AppLogger.debug("Advertise added")
    //    throw new NotImplementedError()
  }
}
