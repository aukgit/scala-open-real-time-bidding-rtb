package shared.com.ortb.demadSidePlatforms

import shared.com.ortb.constants.AppConstants
import shared.com.ortb.model.ImpressionBiddableInfoModel
import shared.com.ortb.model.results.DspBidderRequestModel
import shared.com.ortb.persistent
import shared.com.ortb.persistent.schema.Tables._
import shared.io.loggers.AppLogger
import slick.dbio.Effect
import slick.jdbc.SQLiteProfile.api._
import slick.sql.FixedSqlStreamingAction

trait addNewAdvertiseOnNotFound {
  this : DemandSidePlatformBiddingAgent =>

  def addNewAdvertiseIfNoAdvertiseInTheGivenCriteria(
    request : DspBidderRequestModel,
    isEmpty : Boolean,
    impressionBiddableInfoModel : ImpressionBiddableInfoModel) : Unit = {
    if (!demandSidePlatformConfiguration.isAddNewAdvertiseOnNotFound || !isEmpty) {
      return
    }

    // create DSP -> campaign -> Advertise
    val methodName = "addNewAdvertiseIfNoAdvertiseInTheGivenCriteria"
    AppLogger.debug(methodName, s"Adding Advertise as per configuration, ${ request.bidRequest.toString }")

    val demandSideId = coreProperties.demandSideId
    val repositories = coreProperties.repositories
    val publishersRepository = repositories.publisherRepository
    val campaignRepository = repositories.campaignRepository
    val advertiseRepository = repositories.advertiseRepository

    val publisherByDspQuery : FixedSqlStreamingAction[Seq[persistent.schema.Tables.PublisherRow], persistent.schema.Tables.PublisherRow, Effect.Read] = publishersRepository
      .getAllQuery
      .filter(w => w.demandsideplatformid === coreProperties.demandSideId)
      .take(1)
      .result

    val impressionModel = impressionBiddableInfoModel.impression
    val bidReq = request.bidRequest
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
      demandsideplatformid = demandSideId,
      1,
      isretricttousergender = 0,
      expectedusergender = None,
      publisherid = Some(publisherId))

    val campaignCreated = campaignRepository.add(campaignRow)
    val campaignId = campaignCreated.getIdAsInt
    val contextTextId = 5

    val advertise = AdvertiseRow(
      -1,
      campaignId.get,
      1,
      s"Generated Banner Advertise($bannerString)",
      Some(contextTextId),
      s"BidUrl for $bannerString",
      Some(s"IFrame for $bannerString"),
      0,
      isvideo = 0,
      impressioncount = 0,
      height = simpleBanner.h,
      simpleBanner.w,
      simpleBanner.hmin,
      simpleBanner.wmin,
      simpleBanner.hmax,
      simpleBanner.wmax,
      0,
      Some(0),
      Some(0),
      Some(0))

    advertiseRepository.add(advertise)
    AppLogger.debug("Advertise added")
    //    throw new NotImplementedError()
  }
}
