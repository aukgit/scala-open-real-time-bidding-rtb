package controllers.controllerRoutes

import java.security.spec.InvalidParameterSpecException

import controllers.controllerRoutes.traits.RouterActionPerformByIds
import controllers.webapi.core.AbstractRestWebApi
import play.api.mvc.{ Action, AnyContent }
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._
import shared.com.ortb.enumeration.ControllerDefaultActionType
import shared.com.ortb.model.results.ResultWithBooleanModel
import shared.com.ortb.model.wrappers.http.ControllerGenericActionWrapper
import shared.io.helpers.NumberHelper

import scala.reflect.runtime.universe._

abstract class AbstractGenericRouter[TTable, TRow, TKey : TypeTag](
  controller : AbstractRestWebApi[TTable, TRow, TKey])
  extends SimpleRouter
    with RouterActionPerformByIds {

  val routingActionWrapper : ControllerGenericActionWrapper = ControllerGenericActionWrapper(
    ControllerDefaultActionType.Routing)

  override def routes : Routes = {
    try {
      case GET(p"/") =>
        controller.getAll()

      case POST(p"/create") =>
        controller.add()

      case POST(p"/createMany") =>
        controller.addEntities()

      case POST(p"/createMany/$addTimes") =>
        performAddTimes(addTimes)

      case GET(p"/$id") =>
        performGetById(id)

      case PUT(p"/$id") =>
        performUpdateById(id)

    } catch {
      case e : Exception =>
        controller.handleError(e, routingActionWrapper)
        throw e
    }
  }

  private def performAddTimes(addTimes : String) : Action[AnyContent] = {
    val addTimesAsInt = NumberHelper.isInt(addTimes)
    if (addTimesAsInt.isSuccess) {
      controller.addEntitiesBySinge(addTimesAsInt.result.get)
    }
    else {
      performIntegerConversionFailedAction(addTimesAsInt)
    }
  }

  protected def performIntegerConversionFailedAction(
    stringToInt : ResultWithBooleanModel[Int]) : Action[AnyContent] = {
    val httpFailedActionWrapper = controller.createHttpFailedActionWrapper(
      s"Couldn't convert [$stringToInt] to integer.",
      actionWrapper = routingActionWrapper
    )
    controller.performBadResponseAsAction(
      Some(httpFailedActionWrapper))
  }

  protected def performGetByIdAsInteger(id : String) : Action[AnyContent] = {
    val idAsInt = NumberHelper.isInt(id)

    if (idAsInt.isSuccess) {
      controller.byId(idAsInt.result.get.asInstanceOf[TKey])
    }
    else {
      performIntegerConversionFailedAction(idAsInt)
    }
  }

  protected def performUpdateByIdAsInteger(id : String) : Action[AnyContent] = {
    val idAsInt = NumberHelper.isInt(id)
    // TODO make it more DRY
    if (idAsInt.isSuccess) {
      controller.update(idAsInt.result.get.asInstanceOf[TKey])
    }
    else {
      performIntegerConversionFailedAction(idAsInt)
    }
  }

  override def performUpdateById(
    id : String) : Action[AnyContent] = {
    val typeOfKey = typeOf[TKey]
    typeOfKey match {
      case i if i =:= typeOf[Int] =>
        performUpdateByIdAsInteger(id)
      case i if i =:= typeOf[String] =>
        controller.update(id.asInstanceOf[TKey])
      case _ => throw new InvalidParameterSpecException("Id is not type of Integer or String")
    }
  }

  override def performGetById(
    id : String) : Action[AnyContent] = {
    val typeOfKey = typeOf[TKey]
    typeOfKey match {
      case i if i =:= typeOf[Int] =>
        performGetByIdAsInteger(id)
      case i if i =:= typeOf[String] =>
        controller.byId(id.asInstanceOf[TKey])
      case _ => throw new InvalidParameterSpecException("Id is not type of Integer or String")
    }
  }
}
