package shared.com.ortb.demadSidePlatforms

import shared.com.ortb.demadSidePlatforms.traits.logics.{ DemandSidePlatformBiddingLogic, DemandSidePlatformStaticBidResponseLogic }
import shared.com.ortb.demadSidePlatforms.traits.properties.{ DemandSidePlatformBiddingProperties, DemandSidePlatformCoreProperties }
import shared.com.ortb.enumeration.DemandSidePlatformBiddingAlgorithmType.DemandSidePlatformBiddingAlgorithmType
import shared.com.ortb.importedModels.biddingRequests.{ BannerModel, ImpressionModel, SiteModel }
import shared.com.ortb.manager.AppManager
import shared.com.ortb.manager.traits.DefaultExecutionContextManager
import shared.com.ortb.{ model, persistent }
import shared.com.ortb.persistent.schema.Tables._
import slick.jdbc.SQLiteProfile.api._
import shared.com.ortb.model._
import shared.com.ortb.model.config.DemandSidePlatformConfigurationModel
import shared.com.ortb.model.results.DspBidderRequestModel
import shared.com.ortb.persistent.repositories.AdvertiseRepository
import shared.com.ortb.persistent.schema
import shared.com.ortb.persistent.schema.Tables
import shared.com.repository.traits.FutureToRegular
import shared.io.helpers.EmptyValidateHelper
import shared.io.loggers.AppLogger
import slick.dbio.Effect
import slick.jdbc.SQLiteProfile.api._
import slick.sql.FixedSqlStreamingAction

import scala.concurrent.Future

class DemandSidePlatformBiddingAgent(
  coreProperties : DemandSidePlatformCoreProperties,
  algorithmType : DemandSidePlatformBiddingAlgorithmType)
  extends DemandSidePlatformBiddingLogic
    with DemandSidePlatformBiddingProperties
    with DefaultExecutionContextManager {

  lazy val defaultLimit : Int = coreProperties.demandSidePlatformConfiguration.defaultGenericLimit
  lazy val defaultAdvertiseLimit : Int = coreProperties.demandSidePlatformConfiguration.defaultAdvertiseLimit

  lazy val demandSidePlatformStaticBidResponseLogic : DemandSidePlatformStaticBidResponseLogic = new
      DemandSidePlatformStaticBidResponseLogicImplementation(coreProperties)

  override def getBidPrices(
    request : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel] = ???

  def getLastFailedDeals(
    request : DspBidderRequestModel,
    limit : Int = defaultLimit) : BidFailedReasonsModel = {
    val demandSidePlatformId = coreProperties.demandSideId
    val repositories = coreProperties.repositories
    val lostBids = repositories.lostBids

    val lostBidsSqlProfileAction = lostBids
      .filter(r => r.demandsideplatformid === demandSidePlatformId)
      .sortBy(_.losingprice.desc)
      .sortBy(_.createddate.desc)
      .take(limit)
      .result

    val results = repositories.lostBidRepository
      .run(lostBidsSqlProfileAction)

    BidFailedReasonsModel(
      results
    )
  }

  def appendQueryForBanner(
    advertisesQueryIn : Query[Tables.Advertise, Tables.AdvertiseRow, Seq],
    banner : BannerModel
  ) : Query[Tables.Advertise, Tables.AdvertiseRow, Seq] = {
    var advertisesQuery = advertisesQueryIn
    if (EmptyValidateHelper.isDefined(banner.wmax)) {
      val maxWidth = banner.wmax.get.toDouble
      advertisesQuery = advertisesQuery.filter(ad => ad.maxwidth <= maxWidth)
    }

    if (EmptyValidateHelper.isDefined(banner.wmin)) {
      val minWidth = banner.wmin.get.toDouble
      advertisesQuery = advertisesQuery.filter(ad => ad.minwidth >= minWidth)
    }

    if (EmptyValidateHelper.isDefined(banner.hmax)) {
      val maxHeight = banner.hmax.get.toDouble
      advertisesQuery = advertisesQuery.filter(ad => ad.maxheight <= maxHeight)
    }

    if (EmptyValidateHelper.isDefined(banner.hmin)) {
      val minHeight = banner.hmin.get.toDouble
      advertisesQuery = advertisesQuery.filter(ad => ad.minheight >= minHeight)
    }

    if (EmptyValidateHelper.isDefined(banner.hmin)) {
      val minHeight = banner.hmin.get.toDouble
      advertisesQuery = advertisesQuery.filter(ad => ad.minheight >= minHeight)
    }

    advertisesQuery
  }

  def getImpressionBiddableInfoModel(
    advertisesTable : TableQuery[Tables.Advertise],
    advertiseRepository : AdvertiseRepository,
    impression : ImpressionModel,
    limit : Int = defaultAdvertiseLimit) : Option[ImpressionBiddableInfoModel] = {
    if (EmptyValidateHelper.isEmpty(impression.banner)) {
      val model = ImpressionBiddableInfoModel(
        impression,
        None,
        ImpressionBiddableAttributesModel(
          isBiddable = false,
          hasBanner = false,
          0))

      return Some(model)
    }

    val banner = impression.banner.get
    val advertisesQueryIn : Query[Tables.Advertise, Tables.AdvertiseRow, Seq] = advertisesTable.filter(advertise =>
      advertise.isvideo === 0)

    val advertisesQuery = appendQueryForBanner(advertisesQueryIn, banner)
    val countQuery = advertisesQuery.length.result
    val query = advertisesQuery.take(limit).result

    val totalCount = advertiseRepository.count(countQuery)
    val rows = advertiseRepository.run(query)
    val isBiddable = rows.nonEmpty

    val impressionAttributes = ImpressionBiddableAttributesModel(
      isBiddable,
      hasBanner = true,
      totalCount.get)

    val model = ImpressionBiddableInfoModel(
      impression,
      Some(rows.toArray),
      impressionAttributes)

    Some(model)
  }


  def getBiddableImpressionInfoModels(
    request : DspBidderRequestModel,
    limit : Int = defaultAdvertiseLimit) : Seq[ImpressionBiddableInfoModel] = {
    val repositories = coreProperties.repositories
    val advertiseRepository = repositories.advertiseRepository
    val advertises : TableQuery[Advertise] = repositories.advertises
    val impressions = request.bidRequest.imp.get

    val futureTasks : Seq[Future[ImpressionBiddableInfoModel]] =
      impressions.map(impression => {
        Future(
          getImpressionBiddableInfoModel(
            advertises,
            advertiseRepository,
            impression,
            limit).get
        )
      })

    futureTasks.map(futureTask => FutureToRegular.toRegular(futureTask))
  }

  def addNewAdvertiseIfNoAdvertiseInTheGivenCriteria(
    request : DspBidderRequestModel,
    isEmpty : Boolean,
    impressionBiddableInfoModel : ImpressionBiddableInfoModel) : Unit = {
    if(!demandSidePlatformConfiguration.isAddNewAdvertiseOnNotFound || !isEmpty){
      return
    }

    // create DSP -> campaign ->
    val methodName = "addNewAdvertiseIfNoAdvertiseInTheGivenCriteria"
    AppLogger.debug(methodName, "Adding Advertise as per configuration")

    // get publisher
    val repositories = coreProperties.repositories
    val publishersRepository = repositories.publisherRepository
    val campaignRepository = repositories.campaignRepository
    val advertiseRepository = repositories.advertiseRepository
    val keywordRepository = repositories.keywordRepository
    val keywordAdvertiseMappingRepository = repositories.keywordAdvertiseMappingRepository

    val publisherByDspQuery : FixedSqlStreamingAction[Seq[persistent.schema.Tables.PublisherRow], persistent.schema.Tables.PublisherRow, Effect.Read] = publishersRepository
      .getAllQuery
      .filter(w => w.demandsideplatformid === coreProperties.demandSideId)
      .take(1)
      .result

    val x= impressionBiddableInfoModel.impression
    val bidReq = request.bidRequest
    val site = bidReq.site

    def getCategories(site: Option[SiteModel]) : Option[List[String]] = {
      if(site.isEmpty){
        return None
      }

      if( site.get.cat.isDefined){
        return site.get.cat
      }

      None
    }

    val publisher = publishersRepository
      .getFirstOrDefaultFromQuery(publisherByDspQuery)

    // create campaign
    val banner = x.banner.get.toString
    val categories =  getCategories(site)
//    CampaignRow(-1, s"Generated: Campaign - Banner($banner)",)


  }

  def getImpressionDealsFromBiddableImpressionInfoModels(
    request : DspBidderRequestModel,
    biddableImpressionInfoModels : Seq[ImpressionBiddableInfoModel]):
  Option[List[ImpressionDealModel]] = {
    if(EmptyValidateHelper.isItemsEmpty(Some(biddableImpressionInfoModels))){
      return None
    }

    val list = biddableImpressionInfoModels.map(b => {
      val attr = b.attributes
      val isEmpty = !attr.isBiddable && attr.advertisesFoundCount == 0
      addNewAdvertiseIfNoAdvertiseInTheGivenCriteria(
        request,
        isEmpty,
        b)
    }).toList

    Some(list)
    throw new NotImplementedError()
  }

  override def getBidActual(
    request : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel] = {
    val biddableImpressionInfoModels = getBiddableImpressionInfoModels(
      request : DspBidderRequestModel)

    getImpressionDealsFromBiddableImpressionInfoModels(request, biddableImpressionInfoModels)

    throw new NotImplementedError()
  }

  override def getBidActualNoContent(
    request : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel] = {
    val dspBidderResultModel =
      DemandSidePlatformBidResponseModel(request, request.bidRequest, isNoContent = true)

    val callStackModel = CallStackModel(
      deal = coreProperties.noDealPrice,
      performingAction = s"[getBidActualNoContent] -> No deals."
    )

    dspBidderResultModel.addCallStack(callStackModel)

    Some(dspBidderResultModel)
  }

  override def getBidStatic(request : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel] =
    demandSidePlatformStaticBidResponseLogic.getBidStatic(request)

  override def isStatic : Boolean = demandSidePlatformConfiguration.isStaticSimulate

  lazy override val demandSidePlatformConfiguration : DemandSidePlatformConfigurationModel =
    coreProperties.demandSidePlatformConfiguration

  override def getBidStaticNoContent(request : DspBidderRequestModel) : Option[DemandSidePlatformBidResponseModel] =
    demandSidePlatformStaticBidResponseLogic.getBidStaticNoContent(request)

  lazy override val appManager : AppManager = coreProperties.appManager
}
