package controllers.rtb

import shared.com.ortb.enumeration.DemandSidePlatformBiddingAlgorithmType.DemandSidePlatformBiddingAlgorithmType
import shared.com.ortb.importedModels.biddingRequests.ImpressionModel
import shared.com.ortb.model.results.DspBidderRequestModel
import shared.com.ortb.persistent.schema.Tables._
import shared.io.helpers.EmptyValidateHelper
import slick.jdbc.SQLiteProfile.api._
import com.redis._
import shared.com.ortb.manager.AppManager
import shared.com.ortb.model.config.{ ConfigModel, DomainPortModel }

import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

trait RedisClientWrapper {
  val redisClient : RedisClient
  val redisServer : DomainPortModel
}

class RedisClientImplementation(appManager: AppManager) extends RedisClientWrapper{
  lazy val config : ConfigModel = appManager.config
  lazy val redisServer : DomainPortModel = config.server.redisServer
  lazy val redisClient : RedisClient = new RedisClient(redisServer.domain, 6379)
}

trait BiddingDefaultProperties {
  val defaultIncrementNumber: Double
  val defaultStaticDeal: Double
}

class BiddingDefaultPropertiesImplementation(controller : DemandSidePlatformSimulatorServiceApiController) extends BiddingDefaultProperties{
  lazy val defaultIncrementNumber : Double = controller.config.services.de
  lazy val defaultStaticDeal : Double = 0.03
}

class DspBidderSample1(
  controller : DemandSidePlatformSimulatorServiceApiController,
  algorithmType: DemandSidePlatformBiddingAlgorithmType)
    extends DspBidder {

  override def getBidPrices(
      request: DspBidderRequestModel): Option[DspBidderResultModel] = ???

  override def getBidStaticNoContent(
      request: DspBidderRequestModel): Option[DspBidderResultModel] = {
    val dspBidderResultModel =
      DspBidderResultModel(request, request.bidRequest, isNoContent = true)

    val callStackModel = CallStackModel(
      deal = defaultStaticDeal,
      performingAction = s"[getBidStaticNoContent] -> No deals.",
      isServedAnyDeal = true
    )

    dspBidderResultModel.addCallStack(callStackModel)

    Some(dspBidderResultModel)
  }

  def getLastFailedDeals(request: DspBidderRequestModel,
                         limit: Int = 5): BidFailedReasonsModel = {
    val controller = request.controller
    val demandSidePlatformId = controller.demandSideId
    val repositories = controller.repositories
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

  def biddableImpressionInfo(request: DspBidderRequestModel): Array[ImpressionBiddableInfoModel] = {
    val controller = request.controller
    val demandSidePlatformId = controller.demandSideId
    val repositories = controller.repositories
    val advertises = repositories.advertises
    val impressions = request.bidRequest.imp.get

    impressions.map(impression => {
      if(EmptyValidateHelper.isDefined(impression.banner)){
        val banner = impression.banner

        advertises.filter(advertise =>
          advertise.isvideo === 0)
      }
    })


    throw new NotImplementedError()
  }

  override def getBidActual(
      request: DspBidderRequestModel): Option[DspBidderResultModel] = {
//    val isCurrentRequestBiddable = biddableImpressionInfo(
//      request: DspBidderRequestModel)
    throw new NotImplementedError()
  }

  override def getBidActualNoContent(
      request: DspBidderRequestModel): Option[DspBidderResultModel] = {
    val dspBidderResultModel =
      DspBidderResultModel(request, request.bidRequest, isNoContent = true)

    val callStackModel = CallStackModel(
      deal = defaultStaticDeal,
      performingAction = s"[getBidActualNoContent] -> No deals.",
      isServedAnyDeal = true
    )

    dspBidderResultModel.addCallStack(callStackModel)

    Some(dspBidderResultModel)
  }
}
